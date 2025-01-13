# Use OpenJDK as base image
FROM openjdk:17-jdk-slim as build

# Set working directory in the container
WORKDIR /app

# Copy the project jar file (you need to first build the project using mvn package)
COPY target/Grocery-Booking-Application-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app will be running on (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
