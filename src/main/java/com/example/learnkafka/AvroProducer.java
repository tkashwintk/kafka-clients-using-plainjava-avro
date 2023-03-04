package com.example.learnkafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class AvroProducer {

    private static final Logger log = LoggerFactory.getLogger(AvroProducer.class.getName());

    public static void main(String[] args) {

        // Step 1 - Create producer config
        Properties config = new Properties();
        config.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:9092");
        config.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        config.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        config.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        config.setProperty(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");

        // Step 2 - Create Producer
        KafkaProducer<Integer, Employee> producer = new KafkaProducer<>(config);

        // Step 3 - Produce sample data

        Employee employee = Employee.newBuilder()
                .setId(1003)
                .setName("Ashwin Tk")
                .setExperience(9.5f)
                .setIsAllocated(true)
                .setRole(Role.ARCHITECT)
                .build();

        ProducerRecord<Integer, Employee> record =
                new ProducerRecord<>("ed.employee", 1003, employee);

        log.info("Producing new message");
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    log.info("----- Message produced successfully --------");
                }
            }
        });

        log.info("Close the producer");
        producer.close();

    }
}
