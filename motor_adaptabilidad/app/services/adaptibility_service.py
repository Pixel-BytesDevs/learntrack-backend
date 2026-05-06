# app/services/adaptability_service.py
from .graph_service import GraphService
from app.models.prerecommendation_data import PrerecommendationData, TopicPending
from app.models.topic_pending import TopicPending
from typing import Dict, Set, List, Tuple


class AdaptabilityService:
    def __init__(self, graph_service):
        self.graph_service = graph_service

    def _get_hierarchy_level(self, topic_id, visited=None):
        """
        Calcula el nivel de jerarquía de un tema en base a sus prerrequisitos.
        Un tema sin prerrequisitos tiene nivel 0.
        Un tema con prerrequisitos tiene nivel = max(nivel de sus prerrequisitos) + 1
        """
        if visited is None:
            visited = set()

        # Evitar ciclos
        if topic_id in visited:
            return 0

        visited.add(topic_id)

        # Obtener prerrequisitos
        prereqs = self.graph_service.get_prerequisite_nodes_with_level(topic_id)

        # Si no tiene prerrequisitos, está en el nivel 0 (base)
        if not prereqs:
            return 0

        # Calcular el nivel máximo de los prerrequisitos
        max_prereq_level = 0
        for prereq in prereqs:
            prereq_level = self._get_hierarchy_level(prereq['topicId'], visited.copy())
            max_prereq_level = max(max_prereq_level, prereq_level)

        # El nivel de este tema es uno más que el máximo de sus prerrequisitos
        return max_prereq_level + 1

    def decide_node_to_work(self, prerecommendation_data: PrerecommendationData):

        THRESHOLD = 80

        temas = prerecommendation_data.topic_pendings

        print("=" * 60)
        print("ANÁLISIS DE TEMAS")
        print("=" * 60)

        topics_with_hierarchy = []

        for tema in temas:
            hierarchy_level = self._get_hierarchy_level(tema.topic_id)

            topics_with_hierarchy.append({
                'topic': tema,
                'hierarchy_level': hierarchy_level
            })

            print(f"{tema.topic_name} -> dominio: {tema.domain_level}, jerarquía: {hierarchy_level}")

        # 🔥 1. FILTRAR solo los que NO han alcanzado el umbral
        pendientes = [
            t for t in topics_with_hierarchy
            if t['topic'].domain_level < THRESHOLD
        ]

        print("\nTemas pendientes (<80):", len(pendientes))

        # 🔥 2. SI TODOS ya superaron 80 → no recomendar nada
        if not pendientes:
            print("✓ Todos los temas dominados")
            return None

        # 🔥 3. ORDENAR SOLO POR JERARQUÍA (NO por dominio)
        pendientes_sorted = sorted(
            pendientes,
            key=lambda x: x['hierarchy_level']
        )

        selected = pendientes_sorted[0]

        print("\n✓ TEMA SELECCIONADO:")
        print(f"{selected['topic'].topic_name}")
        print(f"Jerarquía: {selected['hierarchy_level']}")
        print(f"Dominio: {selected['topic'].domain_level}")

        print("=" * 60)

        return selected['topic']