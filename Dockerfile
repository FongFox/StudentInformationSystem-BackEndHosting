# Stage 1: build với Gradle + JDK 21
FROM gradle:jdk21 AS builder
WORKDIR /home/gradle/project

COPY gradlew gradlew.bat gradle/gradle-wrapper.properties build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

COPY src ./src
RUN ./gradlew clean bootJar -x test

# Stage 2: runtime với Temurin JRE 21 trên Alpine
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]