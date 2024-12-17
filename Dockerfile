# Utiliser une image de base avec JDK
FROM openjdk:21-jdk-slim

COPY target/api-0.0.1-SNAPSHOT.jar /app/api-0.0.1-SNAPSHOT.jar 

EXPOSE 8080

# Définir la commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/api-0.0.1-SNAPSHOT.jar"]