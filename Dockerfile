FROM openjdk:latest

EXPOSE 8080

COPY target/*.jar /credit-service/app.jar

VOLUME /credit-service

WORKDIR /credit-service

CMD java -jar app.jar