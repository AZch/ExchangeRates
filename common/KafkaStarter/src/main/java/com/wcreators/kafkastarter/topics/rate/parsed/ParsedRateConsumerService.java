package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.mappers.RateToDtoMapper;
import com.wcreators.kafkastarter.topics.ConsumerService;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParsedRateConsumerService implements ConsumerService<RateDTO> {

    private final RateToDtoMapper mapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @KafkaListener(id = "ParsedRate", topics = {"parsed.EUR-USD"}, containerFactory = "singleParsedRateConsumerFactory")
    public void consume(RateDTO dto) {
        Rate rate = mapper.dtoToModel(dto);
        publisher.publishEvent(rate);
    }
}
