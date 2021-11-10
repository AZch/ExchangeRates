package com.wcreators.kafkastarter.topics.rate.action;

import com.wcreators.kafkastarter.config.KafkaConfig;
import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.kafkastarter.topics.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RateActionTopicConfiguration implements ConfigurationService {

    private final KafkaConfig config;

    @Bean
    public NewTopic actionTopic() {
        return TopicBuilder.name("action.EUR-USD")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, RateActionDTO> kafkaRateActionTemplate() {
        Map<String, Object> factoryConfig = producerFactoryDefaultConfig(config);
        ProducerFactory<String, RateActionDTO> producerFactory = new DefaultKafkaProducerFactory<>(factoryConfig);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleActionRateConsumerFactory() {
        Map<String, Object> factoryConfig = consumerFactoryDefaultConfig(config);
        ConsumerFactory<String, RateActionDTO> consumerFactory = new DefaultKafkaConsumerFactory<>(factoryConfig);
        ConcurrentKafkaListenerContainerFactory<String, RateActionDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
