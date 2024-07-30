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
   - [Backend Deployment](#backend-deployment)
   - [Frontend Deployment](#front-deployment)
   - [Result Check](#result-check)
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

 #Build images and push to dockerhub :
```dockercmd
docker build -t rawaablh/fenleap-backend:1.2 .
docker push rawaablh/fenleap-backend:1.2

```
### Frontend Application
1. **Dockerfile for frontend:**
   
   - Create a Dockerfile for your angular application.
   - Build the Docker image and push it to Docker Hub.
   
   Example Dockerfile:
   ```dockerfile
   # Dockerfile for Spring Boot Backend
# Stage 1: Build the Angular application

```Dockerfile
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
```
 #Build images and push to dockerhub :
```dockercld
docker build -t rawaablh/fenleap-frontend:latest .
docker push rawaablh/fenleap-frontend:latest
```
### Deploy-to-minikube
1. **esnure everything works fine**
```check
minikube version
minikube status
kubectl cluster-info
```
1. **minikube start**
```start
minikube start
```
2. **Deploy postgresql**
   2.1 - First Create the configmap yaml file 
```First Create the configmap yaml file
nano postgres-configmap.yaml
```
Copy the yaml from the ConfigFile Folder \
2.2 - Second  Create the secret yaml file 
```second Create the secret yaml file
nano secret.yaml
```
Copy the yaml from the ConfigFile Folder \
2.3 - Third  Create the deployment yaml file 
```second Create the secret yaml file
nano psql-deployment.yaml
```
Copy the yaml from the ConfigFile Folder
2.4 - fourth configuring the persistante Storage  
Configuring Persistent Storage with PV (Persistent Volumes) and PVC (Persistent Volume Claims) help us  ensure data persistence across pod restarts and failures, safeguarding against data loss in the containerized environment.
THE PV  provides dedicated storage space for the database 
```second Create the pv yaml file
nano psql-pv.yaml
```
Copy the yaml from the ConfigFile Folder\

```second Create the pvc yaml file
nano psql-pvcalim.yaml

```
Copy the psql-pvcalim.yaml from the ConfigFile Folder\
2.5- Deploy
```second
kubectl apply -f psql-deployment.yaml
kubectl apply -f psql-pv.yaml
kubectl apply -f psql-claim.yaml
```
Check for deployment : \
```second
kubectl get deployment
kubectl get pods
```
### Backend-deployment
3. **Deploy backend**
3.1- create the backend deployment file 
```Create the backend yaml file
nano backend-deployment.yaml
```
Copy the yaml from the ConfigFile Folder\
3.2- create the Service file 
```Create the backend-service yaml file
nano backend-service.yaml
```
Copy the yaml from the ConfigFile Folder \

3.3- deploy
```Create the backend-service yaml file
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
```
### Frontend-deployment
4. **Deploy frontend**
4.1- create the front deployment file 
```Create the frontend yaml file
nano frontend-deployment.yaml
```
Copy the yaml from the ConfigFile Folder \
4.2- create the Service file 
```Create the backend-service yaml file
nano frontend-service.yaml
```
Copy the yaml from the ConfigFile Folder \
4.3- deploy
```deploy
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

### Result-check
4. **Access backendr**
```deploy
minikube ip
curl <minikubeip@>:<nodePort>
#for exemple you can check employee list 
curl 192.168.49.2:30005/employee/v1/
```
5. **Access frontend**
```deploy
minikube ip
curl <minikubeip@>:<nodePort>
#for exemple you can check employee list 
curl 192.168.49.2:30000
```
6. **Access backend from your machine browser**
```deploy
 kubectl port-forward --address 0.0.0.0 -n default service/spring-service 30005:8089
```
To check 
<ur-vmIp@>:<backendnodeport>/employee/v1/
for exemple : 
```deploy
http://192.168.1.21:30005/employee/v1/
```
6. **Access frontend from your machine browser**
```deploy
kubectl port-forward --address 0.0.0.0 -n default service/angular 30000:80
```
To check 
<ur-vmIp@>:<frontendnodeport>
for exemple : 
```deploy
http://192.168.1.21:30000
```
