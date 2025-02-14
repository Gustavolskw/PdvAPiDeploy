# Use a lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built JAR from your local machine to the container
COPY target/*.jar /app/application.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/application.jar"]



## Use Maven to build the project before running the application
#FROM maven:3.8.4-openjdk-17-slim AS build
#
#WORKDIR /app
#
## Copy pom.xml and download dependencies first (to cache them)
#COPY pom.xml /app
#RUN mvn dependency:go-offline -B
#
## Copy the source code
#COPY src /app/src
#
## Build the application inside the container
#RUN mvn clean package -DskipTests
#
## Use a lightweight OpenJDK 17 image for runtime
#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
## Copy the compiled JAR from the build stage
#COPY --from=build /app/target/service-0.0.1-SNAPSHOT.jar /app/application.jar
#
## Expose port 8080
#EXPOSE 8080
#
## Run the Spring Boot application
#CMD ["java", "-jar", "/app/application.jar"]
