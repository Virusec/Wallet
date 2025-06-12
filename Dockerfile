# Stage 1: build the application
# Используем официальный Maven с Eclipse Temurin OpenJDK 17
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: create the runtime image
# Базовый образ с Eclipse Temurin OpenJDK 17 JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar wallet-service.jar

# Expose application port and specify entrypoint
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet-service.jar"]