from flask_sqlalchemy import SQLAlchemy

# Crear la instancia de SQLAlchemy
db = SQLAlchemy()

def init_app(app):
    """Inicializa la aplicación con la configuración de la base de datos."""
    db.init_app(app)