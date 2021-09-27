package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.consumer.Consumer;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@RequiredArgsConstructor
public class ParsedRateConsumer {
//
//    private final Consumer consumer;
//
//    @Bean
//    public KafkaListenerContainerFactory<?> singleRateParsedFactory() {
//        ConcurrentKafkaListenerContainerFactory<Long, Rate> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerParsedRateFactory());
//        factory.setBatchListener(false);
//        factory.setMessageConverter(new StringJsonMessageConverter());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<Long, Rate> consumerParsedRateFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumer.getConfig());
//    }
}
