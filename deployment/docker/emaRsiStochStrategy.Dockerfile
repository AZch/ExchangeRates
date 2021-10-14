FROM openjdk:11-slim-buster as app

WORKDIR /app
COPY ./process/StrategyEmaRsiStoch/target/StrategyEmaRsiStoch-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
