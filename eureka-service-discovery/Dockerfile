# Stage 1: Build
FROM maven:3.8.3-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy only the pom.xml first to leverage caching for dependencies
COPY pom.xml ./

# Download dependencies to go offline
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8083
EXPOSE 5007
# Command to run the application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007", "-jar", "app.jar"]
