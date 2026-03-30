# Usamos una imagen de Java ligera
FROM eclipse-temurin:17-jdk-alpine
# Copiamos el archivo JAR (asegúrate de haber hecho 'mvn package' antes)
COPY target/*.jar app.jar
# Exponemos el puerto
EXPOSE 10000
# Comando de ejecución
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app.jar"]