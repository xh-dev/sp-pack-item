FROM maven:3.8-jdk-11 as maven-build
COPY . /app
WORKDIR /app
RUN mvn clean compile package assembly:single

FROM openjdk:11-jdk
COPY --from=maven-build /app/target/app.jar /app/
WORKDIR /app
EXPOSE 8080
CMD java -jar app.jar