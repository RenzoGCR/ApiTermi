# Fase 1: Compilación (Build)
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jdk-focal
COPY --from=build /target/ApiTermi-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto 10000 que es el estándar de Render
EXPOSE 10000
# Comando de arranque vinculando el puerto de Spring con la variable de Render
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]