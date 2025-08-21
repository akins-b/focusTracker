FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build target/focusTracker-0.0.1-SNAPSHOT.jar focusTracker.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "focusTracker.jar"]