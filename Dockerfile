FROM openjdk:17-jdk-alpine

COPY target/Foyer-1.4.2.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app.jar"]

