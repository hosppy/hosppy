FROM gradle:7.6-jdk AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle dependencies
COPY ./src src/
RUN gradle clean bootJar

FROM openjdk:11-jre

COPY --from=builder /app/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]