# MedicineScheduler
## Set up
## Data base
0. https://github.com/mysql/mysql-docker
1. docker pull mysql/mysql-server:5.7
2. docker run --detach --name=mysql1 --env="MYSQL_ROOT_HOST=%" --publish 3306:3306 mysql/mysql-server:5.7
3. docker logs mysql1 2>&1 | grep GENERATED
4. docker exec -it mysql1 mysql -uroot -p (use password from 4)
5. ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
6. ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
7. FLUSH PRIVILEGES;
8. CREATE DATABASE test_db;
9. add entities

## Kafka + Zookeeper
1. docker compose -f deployment/docker/compose-kafka.yaml up -d

## Forex Producer
1. docker build -t fp -f deployment/docker/forexProducer.Dockerfile .
2. docker run --name fp --network=host --user chrome --privileged -d fp

## Ema rsi stoch strategy
1. docker build -t ers -f deployment/docker/emaRsiStochStrategy.Dockerfile .
2. docker run --name ers --network=host -d ers

## Stored parsed and action rate strategy 
1. docker build -t spar -f deployment/docker/storedParsedRates.Dockerfile .
2. docker run --name spar --network=host -d spar