FROM maven:3.6-openjdk-11 as builder
COPY src /src
COPY pom.xml /
RUN mvn package -Djar.name=app

FROM openjdk:11.0.8-jre-slim
EXPOSE 8080
COPY --from=builder /target/app.jar /
ENTRYPOINT ["java", "-jar", "/app.jar"]
