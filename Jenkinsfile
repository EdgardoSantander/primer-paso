pipeline {
    agent any

    // Aquí le dices a Jenkins qué herramientas usar basadas en lo que configuraste en "Tools"
    tools {
        jdk 'JDK-17'
        maven 'Maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Git se usa aquí automáticamente
                checkout scm
            }
        }

        stage('Build Spring Boot') {
            steps {
                // Como Maven ya está configurado arriba, puedes usar el comando directamente
                sh 'mvn clean package -DskipTests'
            }
        }


    }
}