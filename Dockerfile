FROM openjdk:11-jre

COPY build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Shanghai", "/app.jar"]