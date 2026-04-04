# ---------- Stage 1: Build ----------
FROM maven:3.9 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package


# ---------- Stage 2: Runtime ----------
# FROM openjdk:17  ~~ depricated now
# FROM eclipse-temurin:17-jdk    ~~ not lightweight
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]