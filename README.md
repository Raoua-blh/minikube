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
   - [Frontend Deployment](#frontend-deployment)
   - [Result Check](#result-check)
  
4. [Improvements](#improvements)
---

## Step 1: Containerization of Both Applications

### Backend Application

1. **Dockerfile for Backend:**
   
   - Create a Dockerfile for your Java Spring Boot backend application.
   - Build the Docker image and push it to Docker Hub.
   
   Example Dockerfile:
   ```dockerfile
   # Dockerfile for Spring Boot Backend
   FROM openjdk:17-jdk-slim
   WORKDIR /app
   COPY target/Demo-backend-0.0.1-SNAPSHOT.jar Fenleap-backend.jar
   EXPOSE 8089
   ENTRYPOINT ["java", "-jar", "Fenleap-backend.jar"]

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

```Dockerfile
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
```
 #Build images and push to dockerhub :
```dockercld
docker build -t rawaablh/fenleap-frontend:latest .
docker push rawaablh/fenleap-frontend:latest
```

### Docker-images
```dockercld
docker pull  rawaablh/fenleap-backend:1.2 
docker pull rawaablh/fenleap-frontend:latest
```
### Deploy-to-minikube
1. **esnure everything works fine**
```check
minikube version
minikube status
kubectl cluster-info
```
 ### start-minikube
1. **minikube start**
```start
minikube start
```

### postgres-deployment
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
```
#for exemple you can check employee list 
```
curl 192.168.49.2:30005/employee/v1/
```

5. **Access frontend**
```deploy
minikube ip
curl <minikubeip@>:<nodePort>
```
#for exemple you can check employee list 
```
curl 192.168.49.2:30000
```
6. **Access backend from your machine browser**
```deploy
 kubectl port-forward --address 0.0.0.0 -n default service/spring-service 30005:8089
```
To check \

<ur-vmIp@>:<backendnodeport>/employee/v1/
for exemple : 
```deploy
http://192.168.1.21:30005/employee/v1/
```
6. **Access frontend from your machine browser**
```deploy
kubectl port-forward --address 0.0.0.0 -n default service/angular 30000:80
```
To check \
<ur-vmIp@>:<frontendnodeport>
for exemple : 
```deploy
http://192.168.1.21:30000
```

### ⚡⚡ Improvements ⚡⚡


To facilitate the management of Kubernetes deployments, consider using Helm, a package manager for Kubernetes that streamlines the installation and management of Kubernetes applications.

#### ✨Using Helm for Kubernetes Deployment

[Helm](https://helm.sh/) allows you to package Kubernetes applications into Helm Charts, which are easy to create, version, share, and deploy. Here's how you can improve your deployment process with Helm:

1. ✨ **Create Helm Charts for Applications:**
   Helm Charts define, install, and upgrade Kubernetes applications. You can create Helm Charts for your backend and frontend applications. Here's a basic example of a Helm Chart structure:

   
plaintext \
   myapp/ \
   ├── Chart.yaml        # Metadata about the chart \
   ├── values.yaml       # Default configuration values \
   ├── templates/        # Kubernetes manifest templates \
   │   ├── deployment.yaml \
   │   ├── service.yaml \
   │   └── secrets.yaml \
   │   └── configmap.yaml \
   │   └── ingress.yaml \
   │   \
   └── ... \
  
   #### ✨Add Ingress for Applications 
[Ingress](https://kubernetes.io/fr/docs/concepts/services-networking/ingress/) allows external access to services in a Kubernetes cluster. You can configure Ingress to route traffic to your backend and frontend applications. \
 #### ✨Each in his dedicated NameSpace  \
to ensure isolation and security 
#### ✨LoadBalancer
LoadBalancer services provide a reliable way to expose your application externally. 
Distribute incoming network traffic across multiple pods (instances) of your application.  
#### ✨Container Image Security
 Allow deploying containers only from known registries to ensure image authenticity and security. 
 #### ✨Role-based Access Control (RBAC):
 \
 to define fine-grained access policies for Kubernetes resources, restricting access based on roles and permissions. 
 #### ✨Readiness and Liveness Probes:
\
 to ensure Kubernetes knows when your application is ready to serve traffic and when it needs restarting. 
 #### ✨Horizontal Pod Autoscaler (HPA):
 Configure HPAs to automatically scale the number of replicas of your applications based on CPU or custom metrics. 
 #### ✨Monitor Cluster Resources :
 to track resource usage, performance metrics, and detect potential issues. 





   


