package com.wcreators.kafkastarter.producer;

import com.wcreators.kafkastarter.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultProducer implements Producer {

    private final KafkaConfig config;

    @PostConstruct
    public void init() {
        System.out.println(config);
    }

    @Override
    public Map<String, Object> getConfig() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.CLIENT_ID_CONFIG, config.getProducer().getClientId()
        );
    }
}
