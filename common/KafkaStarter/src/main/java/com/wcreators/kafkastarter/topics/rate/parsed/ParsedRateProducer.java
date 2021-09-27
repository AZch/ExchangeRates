package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.producer.Producer;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@RequiredArgsConstructor
public class ParsedRateProducer {

    private final Producer producer;

    @Bean
    public KafkaTemplate<Long, Rate> kafkaRateParsedTemplate() {
        KafkaTemplate<Long, Rate> template = new KafkaTemplate<>(producerRateParsedFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, Rate> producerRateParsedFactory() {
        return new DefaultKafkaProducerFactory<>(producer.getConfig());
    }
}
