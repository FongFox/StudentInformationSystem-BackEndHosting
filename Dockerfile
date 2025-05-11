# ── Stage 1: Build với Gradle + JDK 21 ─────────────────────────────
FROM gradle:8.4.1-jdk21 AS builder
WORKDIR /home/gradle/project

# Copy Wrapper & cấu hình Gradle để tận dụng cache
COPY gradlew gradlew.bat gradle/gradle-wrapper.properties settings.gradle.kts build.gradle.kts ./
RUN chmod +x gradlew

# Copy source, build jar
COPY src ./src
RUN ./gradlew clean bootJar -x test

# ── Stage 2: Runtime với JRE 21 Nhẹ ──────────────────────────────
FROM eclipse-temurin:21-jre-focal
WORKDIR /app

# Copy artifact từ builder
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Môi trường và port
ENV JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
