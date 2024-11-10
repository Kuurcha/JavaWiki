# Use the official Maven image to build the application
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the src directory into the container
COPY pom.xml .
COPY src ./src

# Package the application (using Maven)
RUN mvn clean package -DskipTests

# Use the official OpenJDK image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/eureka-server-discovery-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]