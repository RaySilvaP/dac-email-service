# Use an official JDK base image for building
FROM amazoncorretto:17-alpine3.16-jdk AS build

# Set working directory
WORKDIR /app

# Copy the Maven/Gradle build files and application source code
COPY . .

# Build the application (using Maven or Gradle)
RUN ./mvnw clean package -DskipTests

# Use a minimal JDK runtime for the final image
FROM amazoncorretto:17-alpine3.16-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
