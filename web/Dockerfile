FROM openjdk:17-alpine

ENV TZ=Asia/Shanghai

COPY build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]