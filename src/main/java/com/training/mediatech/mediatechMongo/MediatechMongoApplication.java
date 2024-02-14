package com.training.mediatech.mediatechMongo;

import com.training.mediatech.mediatechMongo.service.CustomerService;
import com.training.mediatech.mediatechMongo.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MediatechMongoApplication implements CommandLineRunner {
    @Autowired
    private KafkaService kafkaService;
    public static void main(String[] args)  {

        SpringApplication.run(MediatechMongoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("Début consommation topic kafka");

        kafkaService.consumeFromKafkaTopic("topic_0");

        System.out.println("Fin consommation topic kafka");

    }

    }
