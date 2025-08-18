# Use lightweight JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy built JAR into container
COPY target/*.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]
