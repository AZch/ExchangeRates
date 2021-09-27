package com.wcreators.kafkastarter.consumer;

import com.wcreators.kafkastarter.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultConsumer implements Consumer {

    private final KafkaConfig config;

    @Override
    public Map<String, Object> getConfig() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, config.getConsumer().getGroupId(),
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
        );
    }
}
