FROM openjdk:17
COPY target/bookstore-docker.jar bookstore-docker.jar
ENTRYPOINT ["java", "-jar","bookstore-docker.jar"]
EXPOSE 8080