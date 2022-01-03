# because this run from script directory
cd ../../
# set up apps
mvn clean install

## copy kubernetes files for apps
scp -r ./deployment/kubernetes/apps server:~/ExchangeRates/deployment/kubernetes/apps

## forex producer
docker build -t fp -f deployment/docker/apps/forexProducer.Dockerfile .
docker tag ers azch97/fp:latest
docker push azch97/fp:latest

ssh server 'kubectl replace --force -f ~/ExchangeRates/deployment/kubernetes/apps/forexProducer/deployment.yml'

## strategy
docker build -t ers -f deployment/docker/apps/emaRsiStochStrategy.Dockerfile .
docker tag ers azch97/ers:latest
docker push azch97/ers:latest

ssh server 'kubectl replace --force -f ~/ExchangeRates/deployment/kubernetes/apps/emaRsiStochStrategy/deployment.yml'

## stored
docker build -t sr -f deployment/docker/apps/storedRates.Dockerfile .
docker tag ers azch97/sr:latest
docker push azch97/sr:latest

ssh server 'kubectl replace --force -f ~/ExchangeRates/deployment/kubernetes/apps/storedData/service.yml'
ssh server 'kubectl replace --force -f ~/ExchangeRates/deployment/kubernetes/apps/storedData/deployment.yml'