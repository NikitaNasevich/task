FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/task-0.0.1-SNAPSHOT.jar /app/flightservice.jar

CMD ["java", "-jar", "/app/flightservice.jar"]

EXPOSE 3000
