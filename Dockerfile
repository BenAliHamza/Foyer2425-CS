# Step 1: Use a lightweight base image for downloading the JAR
FROM eclipse-temurin:17-jre AS downloader

# Define the Nexus repository and artifact details
ARG NEXUS_URL=http://192.168.125.100:8081/repository/maven-releases/
ARG GROUP_ID=tn.esprit
ARG ARTIFACT_ID=Foyer
ARG VERSION=1.4.9-RELEASE

# Convert groupId to the path format used in Nexus (replace dots with slashes)
RUN mkdir -p /app && \
    ARTIFACT_PATH=$(echo $GROUP_ID | tr '.' '/') && \
    ARTIFACT_URL="$NEXUS_URL$ARTIFACT_PATH/$ARTIFACT_ID/$VERSION/$ARTIFACT_ID-$VERSION.jar" && \
    echo "Downloading from: $ARTIFACT_URL" && \
    curl -f -o /app/application.jar "$ARTIFACT_URL"

# Step 2: Use a multi-stage build for the final image
FROM eclipse-temurin:17-jre

# Set a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the downloaded JAR
COPY --from=downloader /app/application.jar /app/application.jar

# Set permissions for the JAR
RUN chown appuser:appuser /app/application.jar && chmod 755 /app/application.jar

# Switch to non-root user
USER appuser

# Expose the port and set the entrypoint
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
