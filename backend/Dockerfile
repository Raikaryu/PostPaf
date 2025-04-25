# Étape de build
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# Copier les fichiers de configuration Gradle pour mise en cache des dépendances
COPY build.gradle settings.gradle.kts ./
COPY gradle.kts ./gradle

# Copier le code source
COPY src ./src

# Compiler l'application
RUN gradle build --no-daemon -x test

# Étape finale avec juste le JRE
FROM openjdk:21-slim

WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "--add-opens=java.base/java.io=ALL-UNNAMED", "-jar", "app.jar"]