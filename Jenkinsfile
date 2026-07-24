pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        maven 'Maven-3'
    }

    environment {
        DOCKER_IMAGE = 'mi-app-springboot'
        FIXED_TAG = '1.0.0'
        CONTAINER_NAME = 'app-springboot-local'
        HOST_PORT = '8080'
        CONTAINER_PORT = '8080'
    }

    stages {
        stage('Clonar Repositorio') {
            steps {
                checkout scm
            }
        }

        stage('Build Spring Boot (Maven)') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Construyendo la imagen de Docker: ${DOCKER_IMAGE}:${FIXED_TAG}..."
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${FIXED_TAG} -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Run Docker Container Locally') {
            steps {
                echo 'Desplegando y arrancando el contenedor localmente...'
                script {
                    // 1. Detiene y elimina el contenedor anterior si ya estaba corriendo para evitar conflictos de puertos
                    sh "docker stop ${CONTAINER_NAME}  true"
                    sh "docker rm ${CONTAINER_NAME}  true"

                    // 2. Ejecuta el nuevo contenedor en segundo plano (-d), mapeando puertos
                    sh """
                        docker run -d \
                          --name ${CONTAINER_NAME} \
                          -p ${HOST_PORT}:${CONTAINER_PORT} \
                          ${DOCKER_IMAGE}:${FIXED_TAG}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "¡Éxito! Tu aplicación Spring Boot está corriendo localmente en el puerto ${HOST_PORT}."
        }
        failure {
            echo 'El pipeline ha fallado en alguna de las etapas.'
        }
    }
}
