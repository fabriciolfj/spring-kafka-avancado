# spring-kafka-avancado

NOTE :
- See lecture with title "Executing Kafka Commands" if you need guidance to execute commands
- If you use windows, change the .sh extension to .bat

# docker exec -it kafka bash

# describe topic
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic t_multi_partitions

# add partition topic
kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic t_multi_partitions --partitions 4

# add topic
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t_employee --partitions 1 --replication-factor 1

# Kafka console consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_employee --offset earliest --partition 0

# add partition to t_rebalance
kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic t_rebalance --partitions 2