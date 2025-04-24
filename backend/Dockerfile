# Utiliser une image de base avec OpenJDK et Gradle
FROM gradle:8.5-jdk21 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers build.gradle et settings.gradle
COPY build.gradle.kts settings.gradle.kts ./

# Copier le reste des fichiers de l'application
COPY . .

# Construire l'application
RUN gradle build --no-daemon

# Utiliser une image de base avec OpenJDK pour exécuter l'application
FROM openjdk:21-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de construction depuis l'image de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port 8081
EXPOSE 8081

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
