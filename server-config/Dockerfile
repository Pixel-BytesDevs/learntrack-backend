# Usamos Java 17 (Cámbialo a 21 si tu proyecto usa Java 21)
FROM eclipse-temurin:21-jre-alpine

# Creamos la carpeta de trabajo
WORKDIR /app

# Copiamos el .jar desde la carpeta target (REEMPLAZA EL NOMBRE AQUÍ)
COPY target/config-server-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8888 que usa tu Config Server
EXPOSE 8888

# Comando para arrancar, limitando la memoria RAM a 256MB para proteger tu EC2
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]