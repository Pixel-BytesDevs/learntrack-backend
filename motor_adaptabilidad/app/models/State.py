from sqlalchemy import Column, Integer, String
from app.config.database import db  # Asegúrate de importar db

class State(db.Model):
    __tablename__ = 'state'

    state_id = Column(Integer, primary_key=True)
    state = Column(String(255), nullable=False, unique=True)

    def __init__(self, state):
        self.state = state
