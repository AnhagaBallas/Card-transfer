FROM openjdk:18
EXPOSE 5500
ADD target/Card-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
