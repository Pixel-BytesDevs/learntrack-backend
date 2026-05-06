# Usamos Java 21 (Cámbialo a 17 si este proyecto usa la 17)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Reemplaza con el nombre exacto del archivo generado en la carpeta target/
COPY target/gestor-grafo-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8083 de este microservicio
EXPOSE 8083

# Arrancamos con un límite de RAM de 512MB
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]