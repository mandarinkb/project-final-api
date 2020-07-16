# Use official base image of Java Runtim
FROM openjdk:8-jdk-alpine

#change timezone
RUN date && \
    apk add tzdata && \
    cp /usr/share/zoneinfo/Asia/Bangkok /etc/localtime && \
    date

# Set volume point to /tmp
VOLUME /tmp

# Make port 8888 available to the world outside container
EXPOSE 8888

# Set application's JAR file
ARG JAR_FILE=project-final-api-0.0.1-SNAPSHOT.jar

# Add the application's JAR file to the container
ADD ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]