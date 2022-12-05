#start with base image containing java runtime
FROM openjdk:11-slim as build
EXPOSE 8080
#Information around who maintains the image
MAINTAINER swagatasinha16@yahoo.in

#add the application's jar to the container
COPY target/book-service-0.0.1-SNAPSHOT.jar book-service-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java", "-jar", "/book-service-0.0.1-SNAPSHOT.jar"]