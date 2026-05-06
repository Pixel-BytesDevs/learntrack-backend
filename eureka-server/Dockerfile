# --- ETAPA 1: Compilación ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiamos archivos de Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Limpieza de finales de línea (por si vienes de Windows) y permisos
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Descargamos dependencias
RUN ./mvnw dependency:go-offline

# Compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: Ejecución ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8761

# Mantenemos tus límites de 256MB para Eureka
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]