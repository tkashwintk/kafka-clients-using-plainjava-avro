Pre-requisite:

1) Install Confluent Kafka. For this, download docker-compose yaml from Confluent website using below command
curl --silent --output docker-compose.yml https://raw.githubusercontent.com/confluentinc/cp-all-in-one/7.2.1-post/cp-all-in-one/docker-compose.yml

2) Bring up Kafka
docker-compose up -d
   
3) Check if Kafka is up
docker-compose ps
   
4) Check the port of control-center and access the web url
http://localhost:XXXX