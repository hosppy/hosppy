FROM gradle:7.6-jdk AS builder

COPY build.gradle settings.gradle ./
RUN gradle dependencies
COPY ./src src/
RUN gradle clean bootJar

FROM openjdk:11-jre

COPY --from=builder build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]