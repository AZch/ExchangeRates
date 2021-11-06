package com.wcreators.kafkastarter.topics.cup.parsed;

import com.wcreators.kafkastarter.config.KafkaConfig;
import com.wcreators.kafkastarter.dto.CupRateDTO;
import com.wcreators.kafkastarter.dto.RateDTO;
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
public class ParsedCupRateTopicConfiguration implements ConfigurationService {

    private final KafkaConfig config;

    @Bean
    public NewTopic parsedCupTopic() {
        return TopicBuilder.name("parsed.CUP-EUR-USD")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, CupRateDTO> kafkaCupRateParsedTemplate() {
        Map<String, Object> factoryConfig = producerFactoryDefaultConfig(config);
        ProducerFactory<String, CupRateDTO> producerFactory = new DefaultKafkaProducerFactory<>(factoryConfig);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<?> parsedCupRateConsumerFactory() {
        Map<String, Object> factoryConfig = consumerFactoryDefaultConfig(config);
        ConsumerFactory<String, CupRateDTO> consumerFactory = new DefaultKafkaConsumerFactory<>(factoryConfig);
        ConcurrentKafkaListenerContainerFactory<String, CupRateDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
