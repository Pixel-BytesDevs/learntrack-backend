# 1. Base de Python ligera
FROM python:3.12.3-slim

# 2. Optimizaciones de Python para Docker
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# 3. Carpeta interna del contenedor
WORKDIR /app

# 4. Copiamos los requerimientos y los instalamos
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 5. Copiamos el resto de la aplicación
COPY . .

# 6. Exponemos el puerto estándar de Flask (5000)
EXPOSE 5000

# 7. Arrancamos con Gunicorn en lugar del servidor de desarrollo de Flask
# IMPORTANTE: "app:app" asume que tu archivo se llama app.py y la variable de Flask se llama app.
# El parámetro -w 2 le dice a Gunicorn que levante 2 workers para procesar peticiones en paralelo.
CMD ["gunicorn", "-w", "2", "-b", "0.0.0.0:5000", "app.main:app"]