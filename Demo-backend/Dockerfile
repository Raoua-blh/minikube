
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Demo-backend-0.0.1-SNAPSHOT.jar Fenleap-backend.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "Fenleap-backend.jar"]
