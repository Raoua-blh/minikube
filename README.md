# Deployment of Java Spring Boot and Angular Application on Kubernetes with Minikube
==================================================================================

## Step 1: Containerization of Both Applications

### Backend Application

1. **Dockerfile for Backend:**
   
   - Create a Dockerfile for your Java Spring Boot backend application.
   - Build the Docker image and push it to Docker Hub.
   
   Example Dockerfile:
   ```dockerfile
   # Dockerfile for Spring Boot Backend
   FROM openjdk:11-jre-slim
   
   WORKDIR /app
   
   COPY target/my-spring-boot-app.jar /app
   
   CMD ["java", "-jar", "my-spring-boot-app.jar"]
 ```Build images and push to dockerhub  :
 # Build images and push to dockerhub :
docker build -t rawaablh/fenleap-backend:1.2 .
docker push rawaablh/fenleap-backend:1.2
