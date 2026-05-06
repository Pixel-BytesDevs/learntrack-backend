FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/auth-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]