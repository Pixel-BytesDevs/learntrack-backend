# --- ETAPA 1: Compilación (Build) ---
# Usamos el JDK completo para poder compilar
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# 1. Copiamos los archivos de configuración de Maven
# Nota: Asegúrate de que la carpeta .mvn y el archivo mvnw estén en tu repo
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Corregimos los permisos y el formato (Vital para que funcione en Linux/AWS)
RUN chmod +x mvnw

# 3. Descargamos las dependencias (esto se cachea para ser más rápido después)
RUN ./mvnw dependency:go-offline

# 4. Copiamos el código fuente y generamos el .jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- ETAPA 2: Imagen Final de Ejecución ---
# Volvemos a tu imagen ligera original (JRE Alpine)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 5. Copiamos solo el resultado de la etapa anterior
# El asterisco ayuda por si la versión del SNAPSHOT cambia
COPY --from=build /app/target/*.jar app.jar

# Tus configuraciones originales
EXPOSE 9003
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]