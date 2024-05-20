
# 1. Building the App with Maven
FROM maven:3.8.3-openjdk-17

ADD . /demo
WORKDIR /demo

# Run Maven build
RUN mvn clean install

FROM openjdk:17.0.2-jdk
COPY --from=0 "/demo/target/backend-0.0.1-SNAPSHOT.jar" app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]