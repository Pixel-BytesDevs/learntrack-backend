from flask import Flask, request, jsonify
from flask_cors import CORS
import requests
import os

from app.services.adaptibility_service import AdaptabilityService
from app.services.graph_service import GraphService
from app.models.prerecommendation_data import PrerecommendationData, LearningStyleResponse, TopicPending
from app.config.database import db
from app.models.Recommendations import Recommendations
from app.models.State import State
def extract_s3_key(s3_url: str) -> str:
    return s3_url.split(".amazonaws.com/")[1].split("?")[0]


def build_s3_url(key: str) -> str:
    return f"https://app-tesis-materiales.s3.us-east-2.amazonaws.com/{key}"

app = Flask(__name__)

# CORS
CORS(app, resources={
    r"/*": {
        "origins": "*",
        "methods": ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
        "allow_headers": ["Content-Type", "Authorization"]
    }
})

# DB CONFIG (⚠️ mover a .env en producción)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv('DATABASE_URL', 'postgresql://postgres:12345@db-postgres:5432/interacciones')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db.init_app(app)

# Services
GRAPH_URL = os.getenv('GRAPH_SERVICE_URL', 'http://gestor-grafo:8083')
graph_service = GraphService(base_url=GRAPH_URL)
adaptability_service = AdaptabilityService(graph_service)

OA_URL = os.getenv('OA_MANAGER_URL', 'http://gestor-oas:8000')
OA_MANAGER_URL = OA_URL

# =========================
# UTILIDAD: obtener estados
# =========================
def get_state(name):
    state = State.query.filter_by(state=name).first()
    if not state:
        state = State(state=name)
        db.session.add(state)
        db.session.flush()
    return state


# =========================
# ADAPTABILITY (GENERAR)
# =========================
@app.route('/adaptability', methods=['POST'])
def adaptability_endpoint():
    try:
        data = request.get_json()
        user_id = data.get('userId')

        # 🔴 Evitar duplicados
        existing = Recommendations.query.join(State).filter(
            Recommendations.user_id == user_id,
            State.state == 'GENERADO'
        ).first()

        if existing:
            return jsonify({
                "success": True,
                "message": "Ya existen recomendaciones pendientes"
            }), 200

        # Mapear datos
        learning_style_responses = [
            LearningStyleResponse(s['styleName'], float(s['porcentaje']))
            for s in data['learningStyleResponses']
        ]

        topic_pendings = [
            TopicPending(t['topicId'], t['topicName'], float(t['domainLevel']))
            for t in data['topicPendings']
        ]

        prerecommendation_data = PrerecommendationData(
            user_id,
            learning_style_responses,
            topic_pendings
        )

        tema = adaptability_service.decide_node_to_work(prerecommendation_data)

        if not tema:
            return jsonify({"success": False, "message": "No hay tema"}), 400

        # Llamar microservicio OA
        oa_response = requests.post(
            f"{OA_MANAGER_URL}/learning-objects/by-topic-and-styles",
            json={
                "topicId": tema.topic_id,
                "topicName": tema.topic_name,
                "learningStyles": [
                    {"styleName": s.style_name, "porcentaje": s.porcentaje}
                    for s in learning_style_responses
                ]
            },
            timeout=10
        )

        oa_response.raise_for_status()
        learning_objects = oa_response.json()

        state_generado = get_state("GENERADO")

        for item in learning_objects.get('results', []):
            oa = item.get("learningObject")
            if not oa:
                continue

            db.session.add(Recommendations(
                state_id=state_generado.state_id,
                user_id=user_id,
                topic_id=tema.topic_id,
                topic_name=tema.topic_name,
                domain_level=tema.domain_level,
                estimated_duration=oa.get('estimatedDuration'),
                file_extension=oa.get('fileExtension'),
                file_name=oa.get('fileName'),
                id_object=oa.get('idObject'),
                level_name=oa.get('levelName'),
                s3_url=oa.get('url'),
                style_name=oa.get('styleName'),
                style_percentage=item.get('porcentaje', 0),
                type_name=oa.get('typeName')
            ))

        db.session.commit()

        return jsonify({
            "success": True,
            "message": "Recomendaciones generadas"
        }), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({"error": str(e)}), 500


# =========================
# GET (POLLING)
# =========================
@app.route('/api/v1/recommendations/<int:userId>', methods=['GET'])
def get_recommendations(userId):
    try:
        state_generado = get_state("GENERADO")

        recommendations = Recommendations.query.filter(
            Recommendations.user_id == userId,
            Recommendations.state_id == state_generado.state_id
        ).all()

        if not recommendations:
            return jsonify({"status": "PENDING"}), 202

        grouped = {}

        for rec in recommendations:
            if rec.topic_id not in grouped:
                grouped[rec.topic_id] = {
                    "topicId": rec.topic_id,
                    "topicName": rec.topic_name,
                    "learningObjects": []
                }
            key = extract_s3_key(rec.s3_url)
            grouped[rec.topic_id]["learningObjects"].append({
                "recommendationId": rec.recommendation_id,
                "fileName": rec.file_name,
                "typeName": rec.type_name,
                "s3Url": build_s3_url(key)
            })

        return jsonify(list(grouped.values())), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# =========================
# FINISH (CAMBIAR ESTADO)
# =========================
@app.route('/api/v1/recommendations/<int:userId>/finish', methods=['POST'])
def finish_recommendations(userId):
    try:
        state_generado = get_state("GENERADO")
        state_completado = get_state("COMPLETADO")

        updated = Recommendations.query.filter(
            Recommendations.user_id == userId,
            Recommendations.state_id == state_generado.state_id
        ).update({
            "state_id": state_completado.state_id
        }, synchronize_session=False)

        db.session.commit()

        return jsonify({
            "message": "Recomendaciones completadas",
            "updated": updated
        }), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({"error": str(e)}), 500


# =========================
# MAIN
# =========================
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)