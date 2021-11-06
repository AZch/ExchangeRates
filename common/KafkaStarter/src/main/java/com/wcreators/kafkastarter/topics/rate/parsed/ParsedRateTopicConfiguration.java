package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.config.KafkaConfig;
import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.topics.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ParsedRateTopicConfiguration implements ConfigurationService {

    private final KafkaConfig config;

    @Bean
    public NewTopic parsedTopic() {
        return TopicBuilder.name("parsed.EUR-USD")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, RateDTO> kafkaRateParsedTemplate() {
        Map<String, Object> factoryConfig = producerFactoryDefaultConfig(config);
        ProducerFactory<String, RateDTO> producerFactory = new DefaultKafkaProducerFactory<>(factoryConfig);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<?> parsedRateConsumerFactory() {
        Map<String, Object> factoryConfig = consumerFactoryDefaultConfig(config);
        ConsumerFactory<String, RateDTO> consumerFactory = new DefaultKafkaConsumerFactory<>(factoryConfig);
        ConcurrentKafkaListenerContainerFactory<String, RateDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
