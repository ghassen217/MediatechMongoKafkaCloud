package com.training.mediatech.mediatechMongo.service;

import com.training.mediatech.mediatechMongo.CustomerEvent;
import com.training.mediatech.mediatechMongo.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    KafkaConfig kafkaConfig;
     public void sendToKafkaTopic(CustomerEvent customer, String topic, String requestType, Producer<String, CustomerEvent> producer) {
        try {
            ProducerRecord<String, CustomerEvent> producerRecord =
                    new ProducerRecord<>(topic, customer);
            producer.send(producerRecord);
            log.info("[{}_CUSTOMER] Message sent to kafka topic {} , data : {} \n", requestType, topic, customer.toString());
            producer.flush();
            producer.close();
        } catch (Exception e) {
            log.error("Error during processing send to kafka topic {} \n", topic, e);
        }
    }

    public void consumeFromKafkaTopic(String topic) {

        try (KafkaConsumer<String, CustomerEvent> consumer = new KafkaConsumer<>(kafkaConfig.kafkaProperties())) {
            consumer.subscribe(Collections.singletonList(topic));

            while (true) {
                ConsumerRecords<String, CustomerEvent> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, CustomerEvent> record : records) {
                    System.out.printf("Topic: %s, Key: %s, Value: %s%n", record.topic(), record.key(), record.value().toString());
                    // Process the record here
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
