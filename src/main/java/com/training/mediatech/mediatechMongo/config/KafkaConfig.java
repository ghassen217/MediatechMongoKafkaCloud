package com.training.mediatech.mediatechMongo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Configuration;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.session.timeout.ms}")
    private int sessionTimeoutMs;

    @Value("${spring.kafka.properties.basic.auth.credentials.source}")
    private String basicAuthCredentialsSource;

    @Value("${spring.kafka.properties.basic.auth.user.info}")
    private String basicAuthUserInfo;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${kafka.schemaRegistryCredentials}")
    private String schemaRegistryCredentials;

    @Bean
    public Properties kafkaProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);
        props.put("sasl.mechanism", saslMechanism);
        props.put("sasl.jaas.config", jaasConfig);
        props.put("security.protocol", securityProtocol);
        props.put("session.timeout.ms", sessionTimeoutMs);
        props.put("basic.auth.credentials.source", basicAuthCredentialsSource);
        props.put("basic.auth.user.info", basicAuthUserInfo);

        // Add the remaining Kafka properties from your original method
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", KafkaAvroSerializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, true);
        props.put("specific.avro.reader", "true");
        props.put("schema.registry.basic.auth.user.info", schemaRegistryCredentials);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("group.id", "groupConsumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        return props;
    }
}
