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