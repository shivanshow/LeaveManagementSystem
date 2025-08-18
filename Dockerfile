# Use lightweight JDK
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot fat jar
COPY target/leavemanagementsystem-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Optional: reduce JVM memory usage
ENV JAVA_OPTS="-Xms128m -Xmx256m"

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
