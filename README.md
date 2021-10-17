# MedicineScheduler
## Build app
1. mvn clean install

## Data base
1. docker build -t db -f deployment/docker/database/mysql.Dockerfile ./deployment/docker/database
2. docker run --name db --env="MYSQL_ROOT_HOST=%" --publish 3306:3306 -d db

## Kafka + Zookeeper
1. docker compose -f deployment/docker/brokers/kafka/compose.yaml up -d
2. docker-compose exec broker kafka-topics \
   --create \
   --bootstrap-server localhost:9092 \
   --replication-factor 1 \
   --partitions 1 \
   --topic parsed.EUR-USD
3. docker-compose exec broker kafka-topics \
   --create \
   --bootstrap-server localhost:9092 \
   --replication-factor 1 \
   --partitions 1 \
   --topic action.EUR-USD

## Forex Producer
1. docker build -t fp -f deployment/docker/apps/forexProducer.Dockerfile .
2. docker run --name fp --network=host --user chrome --privileged -d fp

## Ema rsi stoch strategy
1. docker build -t ers -f deployment/docker/apps/emaRsiStochStrategy.Dockerfile .
2. docker run --name ers --network=host -d ers

## Stored parsed and action rate strategy 
1. docker build -t spar -f deployment/docker/apps/storedParsedRates.Dockerfile .
2. docker run --name spar --network=host -d spar