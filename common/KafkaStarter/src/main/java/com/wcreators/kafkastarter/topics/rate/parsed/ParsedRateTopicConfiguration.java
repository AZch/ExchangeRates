package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.config.KafkaConfig;
import com.wcreators.kafkastarter.dto.RateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ParsedRateTopicConfiguration {

    private final KafkaConfig config;

    @Bean
    public ProducerFactory<String, RateDTO> producerRateParsedFactory() {
        Map<String, Object> factoryConfig = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.CLIENT_ID_CONFIG, config.getProducer().getClientId()
        );
        return new DefaultKafkaProducerFactory<>(factoryConfig);
    }

    @Bean
    public KafkaTemplate<String, RateDTO> kafkaRateParsedTemplate() {
        return new KafkaTemplate<>(producerRateParsedFactory());
    }

    @Bean
    public ConsumerFactory<String, RateDTO> consumerParsedRateFactory() {
        Map<String, Object> factoryConfig = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, config.getConsumer().getGroupId(),
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
        );
        return new DefaultKafkaConsumerFactory<>(factoryConfig);
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleParsedRateConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RateDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerParsedRateFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
