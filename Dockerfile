FROM openjdk:latest

ARG SERVER_PORT

EXPOSE ${SERVER_PORT}

COPY target/*.jar /credit-calculator-api/app.jar

VOLUME /credit-calculator-api

WORKDIR /credit-calculator-api

CMD java -jar app.jar