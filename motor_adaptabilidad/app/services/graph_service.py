# app/services/graph_service.py
import requests


class GraphService:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_prerequisite_nodes_with_level(self, tema_id):
        try:
            # Realizamos la petición GET al endpoint de Spring Boot
            response = requests.get(f"{self.base_url}/topics/required-level/{tema_id}")

            # Verificamos que la respuesta sea exitosa
            response.raise_for_status()

            # Parseamos el cuerpo de la respuesta JSON
            prerrequisitos = response.json()

            # Solo mapeamos topicId y topicName (ignorando el campo 'nivel')
            topics = [
                {
                    "topicId": p['topicId'],  # No necesitamos 'nivel', solo 'topicId' y 'topicName'
                    "topicName": p['topic']
                } for p in prerrequisitos
            ]

            # Retornamos los temas sin el nivel
            return topics

        except requests.exceptions.RequestException as e:
            print(f"Error al obtener los nodos prerequisito con nivel: {e}")
            return []
