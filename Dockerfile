FROM gradle:7.6-jdk-alpine AS builder

COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradlew ./
COPY ./gradle gradle/
COPY ./src src/

RUN ./gradlew clean bootJar

FROM openjdk:11-jre

COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]