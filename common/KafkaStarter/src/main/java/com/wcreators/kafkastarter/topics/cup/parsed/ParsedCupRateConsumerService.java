package com.wcreators.kafkastarter.topics.cup.parsed;

import com.wcreators.kafkastarter.dto.CupRateDTO;
import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.mappers.Mapper;
import com.wcreators.kafkastarter.mappers.RateToDtoMapper;
import com.wcreators.kafkastarter.topics.ConsumerService;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.kafka.topics.parsed-cup-eur-usd.consumer", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ParsedCupRateConsumerService implements ConsumerService<CupRateDTO> {

    private final Mapper<CupRatePoint, CupRateDTO> mapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @KafkaListener(topics = {"parsed.CUP-EUR-USD"}, containerFactory = "parsedCupRateConsumerFactory")
    public void consume(CupRateDTO dto) {
        log.info("receive parsed cup rate");
        CupRatePoint point = mapper.dtoToModel(dto);
        publisher.publishEvent(point);
    }
}
