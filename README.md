# Deployment of Java Spring Boot and Angular Application on Kubernetes with Minikube
==================================================================================
## Table of Contents

1. [Containerization of Both Applications](#containerization-of-both-applications)
   - [Backend Application](#backend-application)
   - [Frontend Application](#frontend-application)
2. [Docker Images](#docker-images)
3. [Deploy to Minikube](#deploy-to-minikube)
   - [Start Minikube](#start-minikube)
   - [Postgres Deployment](#postgres-deployment)

---

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
 ```dockercmd
 #Build images and push to dockerhub :
docker build -t rawaablh/fenleap-backend:1.2 .
docker push rawaablh/fenleap-backend:1.2

### Frontend Application
1. **Dockerfile for frontend:**
   
   - Create a Dockerfile for your angular application.
   - Build the Docker image and push it to Docker Hub.
   
   Example Dockerfile:
   ```dockerfile
   # Dockerfile for Spring Boot Backend
# Stage 1: Build the Angular application
FROM node:16.15 AS builder

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install
# Copy the rest of your application code
COPY . .
# Build the Angular application
RUN npm run build --prod

CMD ["npm", "start"]

# Stage 2: Create the Nginx image to serve the built app
FROM nginx:alpine

COPY --from=builder ./usr/src/app/dist/fenleap-frontend /usr/share/nginx/html
# Copy the build artifacts from the builder stage
EXPOSE 8082

 ```dockercmd
 #Build images and push to dockerhub :
docker build -t rawaablh/fenleap-frontend:latest .
docker push rawaablh/fenleap-frontend:latest
