FROM openjdk:17-jdk-alpine

COPY target/Foyer-1.4.1.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "/app.jar"]

