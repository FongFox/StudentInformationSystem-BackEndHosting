# Stage 1: Build với Gradle + JDK 21
FROM gradle:jdk21 AS builder
WORKDIR /home/gradle/project

# Copy Gradle wrapper & cấu hình
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

# Copy source & build jar
COPY src ./src
RUN ./gradlew clean bootJar -x test

# Stage 2: Runtime với Temurin JRE 21 trên Alpine
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]