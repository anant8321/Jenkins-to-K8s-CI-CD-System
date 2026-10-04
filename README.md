# Jenkins to Kubernetes CI/CD

In this project, I built an end-to-end CI/CD pipeline for a Spring Boot application using **Jenkins, Docker and Kubernetes**.

Here is the main idea: whenever I push a change of my Spring Boot Application to GitHub, Jenkins detects it using **Poll SCM**, builds and tests the application, creates a Docker image, pushes it to Docker Hub, and finally deploys the new version to a local Kubernetes cluster running on **Minikube**.

## Tech Stack

- Java + Spring Boot
- Maven
- Jenkins
- Docker
- Docker Hub
- Kubernetes
- Minikube
- kubectl

## How the pipeline works

```text
GitHub
   ↓
Jenkins (Poll SCM)
   ↓
Maven Build & Test
   ↓
Docker Image Build
   ↓
Push Image to Docker Hub
   ↓
kubectl
   ↓
Kubernetes (Minikube)
   ↓
Deployment + Service
   ↓
Spring Boot Application
```

### 1. GitHub

The complete source code, Dockerfile, Jenkinsfile and Kubernetes manifests are stored in this repository.

### 2. Jenkins

Jenkins periodically checks the GitHub repository using **Poll SCM**. When it detects a new commit, it starts the pipeline defined in `jenkins/Jenkinsfile`.

### 3. Build and Test

Maven is used to compile the Spring Boot application, resolve dependencies, run tests and create the JAR artifact.

### 4. Docker

The application is containerized using a **multi-stage Docker build**. The final image contains the Java runtime and the generated JAR, without the unnecessary build environment.

### 5. Docker Hub

Jenkins tags the image using the Jenkins build number and pushes it to Docker Hub. This gives each build a separate image version.

### 6. Kubernetes

Jenkins uses `kubectl` to deploy the new image to a local **Minikube** cluster. The Kubernetes `Deployment` manages the application Pods, while the `Service` provides a stable way to access them.

Kubernetes also performs a rolling update when a new image version is deployed.

## Project Structure

```text
.
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
│
├── jenkins/
│   └── Jenkinsfile
│
├── k8s/
│   ├── deployment.yml
│   └── service.yml
│
├── src/
│   └── main/
│       ├── java/com/platform/
│       └── resources/
│
├── .dockerignore
├── .gitattributes
├── .gitignore
├── Dockerfile
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Running the project

### Start Minikube

```bash
minikube start
kubectl get nodes
```

### Run Jenkins

Jenkins is run in Docker with access to the host Docker daemon and Kubernetes kubeconfig.

After Jenkins is configured, create a Pipeline job and point it to this repository and the Jenkinsfile.

### Trigger the pipeline

The Jenkins job uses **Poll SCM**. For example:

```text
H/2 * * * *
```

Jenkins checks the repository periodically and starts a new build when it finds a change.

## Kubernetes verification

After a successful pipeline run:

```bash
kubectl get deployments
kubectl get pods
kubectl get services
```

To open the application through Minikube:

```bash
minikube service <service-name>
```

## What I learned

By this project, I practised how **CI/CD, containerization and Kubernetes fit together in a real deployment flow** instead of treating them as separate tools.

The complete flow is:

**Code → Build → Test → Docker Image → Docker Hub → Kubernetes Deployment**

## Future Improvements

- Helm for Kubernetes package management
- Prometheus + Grafana for monitoring
- Terraform for infrastructure provisioning
- AWS/EKS for a cloud-based deployment
