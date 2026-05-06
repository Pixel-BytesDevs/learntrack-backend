# app/models/topic_pending.py

class TopicPending:
    def __init__(self, topic_id: str, topic_name: str, domain_level: float):
        self.topic_id = topic_id
        self.topic_name = topic_name
        self.domain_level = domain_level

    def __repr__(self):
        return f"TopicPending({self.topic_id}, {self.topic_name}, {self.domain_level})"
