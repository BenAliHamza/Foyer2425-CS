# Étape 1 : Construction avec Maven et OpenJDK 17
FROM maven:3.8.7-openjdk-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copier le code source dans l'image
COPY src ./src

# Construire le projet et créer le fichier JAR
RUN mvn clean package -DskipTests

# Étape 2 : Exécution avec une image OpenJDK optimisée
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application sera accessible
EXPOSE 8086

# Démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]