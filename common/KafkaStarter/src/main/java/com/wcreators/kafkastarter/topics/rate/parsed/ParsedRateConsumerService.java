package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.mappers.Mapper;
import com.wcreators.kafkastarter.mappers.RateToDtoMapper;
import com.wcreators.kafkastarter.topics.ConsumerService;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.kafka.topics.parsed-eur-usd.consumer", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ParsedRateConsumerService implements ConsumerService<RateDTO> {

    private final Mapper<Rate, RateDTO> mapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @KafkaListener(topics = {"parsed.EUR-USD"}, containerFactory = "parsedRateConsumerFactory")
    public void consume(RateDTO dto) {
        log.info("receive parsed rate {}", dto);
        Rate rate = mapper.dtoToModel(dto);
        publisher.publishEvent(rate);
    }
}
