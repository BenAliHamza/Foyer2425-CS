# Utilisation de l'image de base openjdk 17
FROM openjdk:17-jdk-alpine

# Exposer le port de l'application Spring Boot
EXPOSE 8086
# Ajouter le fichier JAR de l'application Spring Boot
ADD target/Foyer2425-CS-devops-1.0.jar Foyer2425-CS-devops-1.0.jar

# Exécuter l'application Spring Boot en utilisant Java
ENTRYPOINT ["java", "-jar", "/Foyer2425-CS-devops-1.0.jar"]