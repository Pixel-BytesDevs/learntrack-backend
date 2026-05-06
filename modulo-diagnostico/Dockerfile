# Usamos Java 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Reemplaza con el nombre exacto de tu .jar
COPY target/modulo-diagnostico-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto de este módulo
EXPOSE 8092

# Este módulo parece hacer varias peticiones HTTP externas, le damos 512MB
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]