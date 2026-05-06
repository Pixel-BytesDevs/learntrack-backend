# Usamos Java 17 (Cámbialo a 21 si tu proyecto lo requiere)
FROM eclipse-temurin:21-jre-alpine

# Carpeta de trabajo
WORKDIR /app

# Copiamos el .jar generado (REEMPLAZA EL NOMBRE POR EL TUYO)
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto de Eureka
EXPOSE 8761

# Arrancamos limitando la memoria a 256MB
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]