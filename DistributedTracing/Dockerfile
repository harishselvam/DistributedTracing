FROM eclipse-temurin:17-jdk-alpine

# Copy the app
WORKDIR /app

ENV OTEL_AGENT_VERSION 1.32.0
RUN wget -P /app \
    https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar


COPY build/libs/DistributedTracing-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
# Download the OpenTelemetry Java agent
# Run the app
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar" , "-jar", "app.jar"]

