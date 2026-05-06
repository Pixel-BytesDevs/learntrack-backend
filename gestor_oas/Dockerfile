# 1. Usamos una imagen oficial y ligera de Python (ajusta la versión 3.10 si usas otra)
FROM python:3.12.3-slim

# 2. Variables de entorno para que Python sea más eficiente en Docker
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# 3. Directorio de trabajo
WORKDIR /app

# 4. Copiamos PRIMERO el archivo de requerimientos
# (Hacerlo en este orden hace que Docker guarde las librerías en caché y el build sea súper rápido)
COPY requirements.txt .

# 5. Instalamos las dependencias
RUN pip install --no-cache-dir -r requirements.txt

# 6. Copiamos el resto de tu código fuente
COPY . .

# 7. Exponemos el puerto de FastAPI (usualmente el 8000)
EXPOSE 8000

# 8. Comando para arrancar el servidor con Uvicorn
# IMPORTANTE: Asegúrate de que "main:app" coincida con tu archivo (main.py) y tu variable (app = FastAPI())
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]