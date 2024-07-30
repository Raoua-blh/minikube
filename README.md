# Déploiement d'une Application Java Spring Boot et Angular sur Kubernetes avec Minikube
## First Step Conteneurization of both Front and Back app 
we started by creating Dockerfile for each project  and build the docker image than push it to dockerhub 
## Docker Images :  
You can pull images from : 
backend :
```bash 
 docker pull rawaablh/fenleap-backend:1.2

frontend: 
```bash 
 docker pull rawaablh/fenleap-frontend:latest

## Deploy to minikube :  
1. **minikube start**
2. **postgres deployment**
first we start by creating the configmap 
```bash 
nano postgres-configmap.yaml 
