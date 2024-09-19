FROM openjdk:latest

ARG SERVER_PORT

EXPOSE ${SERVER_PORT}

COPY target/*.jar /credit-calculator-api/app.jar

VOLUME /credit-calculator-api

WORKDIR /credit-calculator-api

CMD java -jar app.jar

HEALTHCHECK \
--interval=30s \
--timeout=5s \
--start-period=120s \
--retries=3 \
CMD curl -f http://localhost:${SERVER_PORT}/actuator/health || exit 1