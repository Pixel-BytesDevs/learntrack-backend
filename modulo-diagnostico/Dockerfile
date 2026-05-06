# --- ETAPA 1: Compilación ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiamos archivos de Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Limpieza de finales de línea y permisos (Vital para EC2)
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Descarga de dependencias
RUN ./mvnw dependency:go-offline

# Compilación del proyecto
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: Ejecución ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8092

# Mantenemos tus límites de 512MB
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]