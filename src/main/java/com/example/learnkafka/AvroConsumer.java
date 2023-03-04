package com.example.learnkafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class AvroConsumer {

    private static final Logger log = LoggerFactory.getLogger(AvroConsumer.class.getName());

    public static void main(String[] args) {

        // Step 1 - Create consumer properties
        Properties config = new Properties();
        config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:9092");
        config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "employee-consumer");
        config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        config.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
        config.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        // Step 2 - Create consumer
        KafkaConsumer<Integer, Employee> consumer = new KafkaConsumer<>(config);

        // Step 3 - Subscribe to Topics
        consumer.subscribe(Arrays.asList("ed.employee"));

        // Step 4 - Poll messages
        while (true) {
            log.info("----- Polling messages -------");
            ConsumerRecords<Integer, Employee> consumerRecords = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<Integer, Employee> consumerRecord: consumerRecords) {
                Integer key = consumerRecord.key();
                Employee employee = consumerRecord.value();
                log.info("Employee details: {}", employee.toString());
            }
            consumer.commitSync();
        }
    }
}
