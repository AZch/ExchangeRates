FROM openjdk:11-slim-buster as app

WORKDIR /app
COPY ./consumers/StoreParsedRates/target/StoreParsedRates-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
