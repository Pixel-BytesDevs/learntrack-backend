
from typing import List
from decimal import Decimal

class LearningStyleResponse:
    def __init__(self, style_name: str, porcentaje: Decimal):
        self.style_name = style_name
        self.porcentaje = porcentaje

class TopicPending:
    def __init__(self, topic_id: str, topic_name: str, domain_level: Decimal):
        self.topic_id = topic_id
        self.topic_name = topic_name
        self.domain_level = max(1, min(100, domain_level))

class PrerecommendationData:
    def __init__(self, user_id: int, learning_style_responses: List[LearningStyleResponse], topic_pendings: List[TopicPending]):
        self.user_id = user_id
        self.learning_style_responses = learning_style_responses
        self.topic_pendings = topic_pendings
