# --- ETAPA 1: Compilación ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Limpieza y permisos
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: Ejecución ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8083

# Mantenemos tus límites de 512MB para el Gestor de Grafos
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]