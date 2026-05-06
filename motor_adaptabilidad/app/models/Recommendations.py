from sqlalchemy import Column, Integer, String, Float, ForeignKey, DateTime
from app.config.database import db  # Asegúrate de importar db
from datetime import datetime

class Recommendations(db.Model):
    __tablename__ = 'recommendations'

    recommendation_id = Column(Integer, primary_key=True)
    state_id = Column(Integer, ForeignKey('state.state_id'))
    user_id = Column(Integer, nullable=False)
    topic_id = Column(String(10), nullable=False)
    topic_name = Column(String(255), nullable=False)
    domain_level = Column(String(255), nullable=False)
    estimated_duration = Column(Integer)
    file_extension = Column(String(10))
    file_name = Column(String(255))
    id_object = Column(Integer)
    level_name = Column(String(255))
    s3_url = Column(String(255))
    style_name = Column(String(255))
    style_percentage = Column(Float)
    type_name = Column(String(255))
    created_at = Column(DateTime, default=datetime.utcnow)

    # Relación con el modelo State
    state = db.relationship("State", backref="recommendations")

    def __init__(self, state_id, user_id, topic_id, topic_name, domain_level, estimated_duration,
                 file_extension, file_name, id_object, level_name, s3_url, style_name, style_percentage, type_name):
        self.state_id = state_id
        self.user_id = user_id
        self.topic_id = topic_id
        self.topic_name = topic_name
        self.domain_level = domain_level
        self.estimated_duration = estimated_duration
        self.file_extension = file_extension
        self.file_name = file_name
        self.id_object = id_object
        self.level_name = level_name
        self.s3_url = s3_url
        self.style_name = style_name
        self.style_percentage = style_percentage
        self.type_name = type_name
