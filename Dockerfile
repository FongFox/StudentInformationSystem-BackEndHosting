# ── Stage 1: Build với Gradle + JDK 21 ─────────────────────────────
FROM gradle:jdk21 AS builder                             # gradle:jdk21 có JDK21 + Gradle mới nhất :contentReference[oaicite:0]{index=0}
WORKDIR /home/gradle/project

COPY gradlew gradlew.bat gradle/gradle-wrapper.properties settings.gradle.kts build.gradle.kts ./
RUN chmod +x gradlew

COPY src ./src
RUN ./gradlew clean bootJar -x test

# ── Stage 2: Runtime với Temurin JRE 21 (Alpine) ────────────────
FROM eclipse-temurin:21-jre-alpine AS runtime               # alpine nhỏ gọn, JRE21 :contentReference[oaicite:1]{index=1}
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
