##FROM maven:3.8.5-openjdk-17 as build
##COPY . .
##RUN mvn clean package -DskipTests
##
##FROM openjdk:17.0.1-jdk-slim
##COPY --from=build /target/chat-back-0.0.1-SNAPSHOT.jar chat-back.jar
##EXPOSE 8080
##ENTRYPOINT ["java","-jar","chat-back.jar"]
#
## Use a Java base image
#FROM openjdk:17-alpine
#
## Set the working directory to /app
#WORKDIR /app
#
## Copy the Spring Boot application JAR file into the Docker image
#COPY target/chat-back-0.0.1-SNAPSHOT.jar /app/chat-back-0.0.1-SNAPSHOT.jar
#
## Set environment variables
#ENV SERVER_PORT=8080
#ENV LOGGING_LEVEL=INFO
#
## Expose the port that the Spring Boot application is listening on
#EXPOSE 8080
#
## Run the Spring Boot application when the container starts
#CMD ["java", "-jar", "chat-back-0.0.1-SNAPSHOT.jar"]
# Используем образ с JDK для этапа сборки
# Use the latest Maven base image for the build stage
#FROM maven:latest AS builder
#
## Copy the project files to the container and set the working directory
#COPY . /usr/src/app
#WORKDIR /usr/src/app
#
## Run Maven to build the project
#RUN mvn clean package -DskipTests  --errors --debug
#
#
## Use the latest OpenJDK image for the runtime stage
#FROM openjdk:22
#
## Copy the built jar file from the builder stage
#COPY --from=builder /usr/src/app/targetchat-back-0.0.1-SNAPSHOT.jar /app/app.jar
#
## Expose the port the app runs on
#EXPOSE 8080
#
## Set the command to execute the app
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM openjdk:18-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/chat-back-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
