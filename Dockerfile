FROM openjdk:23-jdk-slim

WORKDIR /app

COPY /build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]