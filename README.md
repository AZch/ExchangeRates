# MedicineScheduler
## Build app
1. mvn clean install

## Kafka + Zookeeper
1. docker-compose -f deployment/docker/brokers/kafka/compose.yml up -d
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
4. docker-compose exec broker kafka-topics \
   --create \
   --bootstrap-server localhost:9092 \
   --replication-factor 1 \
   --partitions 1 \
   --topic parsed.CUP-EUR-USD

## Data base
1. docker build -t db -f deployment/docker/database/mysql.Dockerfile ./deployment/docker/database
2. docker run --name db --env="MYSQL_ROOT_HOST=%" --publish 3306:3306 -d db

## docker
### Forex Producer
1. docker build -t fp -f deployment/docker/apps/forexProducer.Dockerfile .
2. docker run --name fp --network=host --user chrome --privileged -d fp

### Ema rsi stoch strategy
1. docker build -t ers -f deployment/docker/apps/emaRsiStochStrategy.Dockerfile .
2. docker run --name ers --network=host -d ers

### Stored parsed and action rate strategy 
1. docker build -t sr -f deployment/docker/apps/storedRates.Dockerfile .
2. docker run --name sr --network=host -d sr

## kubernetes
1. minikube start
2. alias k=kubectl
3. eval $(minikube docker-env)

### Database
1. run database in docker
2. k replace --force -f deployment/kubernetes/database/mysql/service.yml

### Kafka
1. run kafka in docker
2. k replace --force -f deployment/kubernetes/broker/kafka/service.yml

### Forex Producer
1. Build docker image
2. k replace --force -f deployment/kubernetes/apps/forexProducer/deployment.yml

### Ema rsi stoch strategy
1. Build docker image
2. k replace --force -f deployment/kubernetes/apps/emaRsiStochStrategy/deployment.yml

### Stored parsed and action rate strategy
1. Build docker image
2. k replace --force -f deployment/kubernetes/apps/storedData/service.yml 
3. k replace --force -f deployment/kubernetes/apps/storedData/deployment.yml



minikube ssh 'grep host.minikube.internal /etc/hosts | cut -f1'   
  

mvn clean install
docker build -t sr -f deployment/docker/apps/storedRates.Dockerfile .
k replace --force -f deployment/kubernetes/apps/storedData/deployment.yml