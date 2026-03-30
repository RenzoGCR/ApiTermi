# Fase de compilación
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM eclipse-temurin:17-jdk-focal
COPY --from=build /target/ApiTermi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java","-jar","app.jar","--server.port=10000"]