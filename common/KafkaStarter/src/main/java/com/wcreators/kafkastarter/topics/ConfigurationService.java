package com.wcreators.kafkastarter.topics;

import com.wcreators.kafkastarter.config.KafkaConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

public interface ConfigurationService {
    default Map<String, Object> consumerFactoryDefaultConfig(KafkaConfig config) {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ConsumerConfig.GROUP_ID_CONFIG, config.getConsumer().getGroupId(),
//                ConsumerConfig.CLIENT_ID_CONFIG, config.getConsumer().getClientId(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true,
                JsonDeserializer.TRUSTED_PACKAGES, "*",
                CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT",
                SaslConfigs.SASL_MECHANISM, "PLAIN",
                SaslConfigs.SASL_JAAS_CONFIG, String.format(
                        "%s required username=\"%s\" " + "password=\"%s\";", PlainLoginModule.class.getName(), "admin", "admin-secret"
                )
        );
    }

    default Map<String, Object> producerFactoryDefaultConfig(KafkaConfig config) {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.CLIENT_ID_CONFIG, config.getProducer().getClientId(),
                CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT",
                SaslConfigs.SASL_MECHANISM, "PLAIN",
                SaslConfigs.SASL_JAAS_CONFIG, String.format(
                        "%s required username=\"%s\" " + "password=\"%s\";", PlainLoginModule.class.getName(), "admin", "admin-secret"
                )
        );
    }
}
