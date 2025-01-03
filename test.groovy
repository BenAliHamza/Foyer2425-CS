pipeline {
    agent any

    options {
        ansiColor('xterm') // Enable colored logs for better visibility
        timestamps() // Add timestamps to logs
    }

    environment {
        REPORT_DIR = 'target/surefire-reports'
        SONAR_TOKEN = credentials('SonarQube-token') // Use the credentials ID for SonarQube token
        GIT_CREDENTIALS = 'github-credentials' // Use the credentials ID for GitHub
        DOCKER_COMPOSE_FILE = "docker-compose.yml" // Update if your file has a different name
        SERVICE_NAME = "springboot-backend"       // Name of the service to build
        IMAGE_NAME = "hamzabenali33/springboot-backend" // Replace with your Docker Hub repository
        NEXUS_DOCKERFILE = "Dockerfile.nexus"
        IMAGE_VERSION = "" // Placeholder for dynamically generated version
        DOCKER_CREDENTIALS_ID = "docker-hub-credentials-id"      // Set up Docker Hub credentials in Jenkins
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "\u001B[34mCleaning workspace...\u001B[0m" // Blue log
                cleanWs()
            }
        }

        stage('Start Nexus and SonarQube Containers') {
            steps {
                script {
                    // Nexus Container ID
                    def nexusContainer = 'f2b356302285'

                    // SonarQube Container ID
                    def sonarqubeContainer = '69fcede98a6a'

                    // Start Nexus if not running
                    echo "Checking Nexus container status..."
                    def nexusStatus = sh(script: "docker inspect -f '{{.State.Running}}' ${nexusContainer}", returnStdout: true).trim()
                    if (nexusStatus != 'true') {
                        echo "Nexus is not running. Starting the container..."
                        sh "docker start ${nexusContainer}"
                    } else {
                        echo "Nexus is already running."
                    }

                    // Start SonarQube if not running
                    echo "Checking SonarQube container status..."
                    def sonarqubeStatus = sh(script: "docker inspect -f '{{.State.Running}}' ${sonarqubeContainer}", returnStdout: true).trim()
                    if (sonarqubeStatus != 'true') {
                        echo "SonarQube is not running. Starting the container..."
                        sh "docker start ${sonarqubeContainer}"
                    } else {
                        echo "SonarQube is already running."
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                echo "\u001B[36mCloning the repository...\u001B[0m" // Cyan log
                git credentialsId: "${GIT_CREDENTIALS}", url: 'https://github.com/BenAliHamza/Foyer2425-CS.git', branch: 'hamza'

                echo "\u001B[36mPulling latest changes...\u001B[0m" // Cyan log
                sh 'git pull origin hamza'
            }
        }

        stage('Increment Version') {
            steps {
                script {
                    echo "\u001B[33mIncrementing project version...\u001B[0m" // Yellow log

                    // Extract the current version
                    def currentVersion = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    echo "Current version: ${currentVersion}"

                    // Increment the version
                    def versionParts = currentVersion.replace("-RELEASE", "").split("\\.")
                    def newVersion = "${versionParts[0]}.${versionParts[1]}.${(versionParts[2] as int) + 1}-RELEASE"
                    echo "New version: ${newVersion}"

                    // Update the pom.xml with the new version
                    sh "mvn versions:set -DnewVersion=${newVersion} -DgenerateBackupPoms=false"
                    // Update the environment variable
                    IMAGE_VERSION = newVersion

                    // Add debugging logs for Git credentials
                    echo "\u001B[34mSetting up Git credentials...\u001B[0m" // Blue log
                    sh 'git config --list' // List all current Git configurations for debugging

                    // Explicitly set Git credentials
                    withCredentials([usernamePassword(credentialsId: "${GIT_CREDENTIALS}", usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                        sh """
                            git config user.name 'Jenkins CI'
                            git config user.email 'jenkins@example.com'
                            git config credential.helper 'store'
                            git remote set-url origin https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/BenAliHamza/Foyer2425-CS.git
                            git add pom.xml
                            git commit -m 'Incremented version to ${newVersion}'
                            git push origin hamza
                        """
                    }
                }
            }
        }

        stage('Maven Version') {
            steps {
                echo "\u001B[32mChecking Maven version...\u001B[0m" // Green log
                sh 'mvn -version'
            }
        }

        stage('Maven Clean') {
            steps {
                echo "\u001B[36mRunning Maven clean...\u001B[0m" // Cyan log
                sh 'mvn clean'
            }
        }

        stage('Maven Compile') {
            steps {
                echo "\u001B[33mCompiling the project...\u001B[0m" // Yellow log
                sh 'mvn compile'
            }
        }

        stage('Maven Test') {
            steps {
                echo "\u001B[35mRunning tests...\u001B[0m" // Magenta log
                sh 'mvn test'
            }
            post {
                always {
                    junit "${REPORT_DIR}/*.xml"
                }
                failure {
                    echo "\u001B[31mTests failed. Check the reports for details.\u001B[0m" // Red log
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "\u001B[36mRunning SonarQube analysis...\u001B[0m" // Cyan log
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }

        stage('Build and Deploy NEXUS') {
            steps {
                script {
                    // Run Maven deploy and skip tests
                    sh 'mvn clean deploy -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image..."
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} ."
                }
            }
        }

        // Docker login step before pushing image
        stage('Docker Login') {
            steps {
                script {
                    echo "Logging in to Docker Hub..."
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                                docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD docker.io
                            '''
                    }
                }
            }
        }


        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing the Docker image to Docker Hub..."
                    try {
                        echo "Using Docker Hub credentials to push the image..."
                        echo "${IMAGE_NAME}:${IMAGE_VERSION}"
                        sh "docker push ${IMAGE_NAME}:${IMAGE_VERSION}"


                    } catch (Exception e) {
                        echo "Docker push failed with error: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Build Runtime Image') {
            steps {
                script {
                    echo "Building the runtime image from Nexus JAR using Dockerfile.nexus..."
                    // Build the Docker image with the "latest" tag
                    sh "docker build -f Dockerfile -t ${IMAGE_NAME}:latest ."
                }
            }
        }





        stage('Deploy Application') {
            steps {
                script {

                    echo "Deploying the application using the updated Docker image..."

                    // Stop and remove existing containers
                    sh """
                                docker-compose -f ${DOCKER_COMPOSE_FILE} down
                                """

                    // Update the docker-compose.yml with the new image version
                    sh """
                                docker-compose -f ${DOCKER_COMPOSE_FILE} pull
                                docker-compose -f ${DOCKER_COMPOSE_FILE} up -d
                                """

                }
            }
        }

    }

    post {
        always {
            echo "\u001B[36mPipeline finished. Cleaning up workspace...\u001B[0m" // Cyan log
            cleanWs()
        }
        success {
            echo "\u001B[32mBuild succeeded! All tests passed.\u001B[0m" // Green log
        }
        failure {
            echo "\u001B[31mBuild failed. Check logs and reports for details.\u001B[0m" // Red log
        }
    }
}
