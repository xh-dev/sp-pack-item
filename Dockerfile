FROM maven:3.8-amazoncorretto-17 as maven-build
COPY . /app
WORKDIR /app
RUN mvn clean compile package assembly:single

FROM amazoncorretto:17
COPY --from=maven-build /app/target/app.jar /app/
WORKDIR /app
EXPOSE 8080
CMD java -jar app.jar