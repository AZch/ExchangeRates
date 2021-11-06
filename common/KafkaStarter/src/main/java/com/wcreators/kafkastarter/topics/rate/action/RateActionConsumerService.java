package com.wcreators.kafkastarter.topics.rate.action;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.kafkastarter.mappers.Mapper;
import com.wcreators.kafkastarter.mappers.RateActionToDtoMapper;
import com.wcreators.kafkastarter.topics.ConsumerService;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.kafka.topics.action-eur-usd.consumer", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class RateActionConsumerService implements ConsumerService<RateActionDTO> {

    private final Mapper<RateAction, RateActionDTO> mapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @KafkaListener(topics = {"action.EUR-USD"}, containerFactory = "singleActionRateConsumerFactory")
    public void consume(RateActionDTO dto) {
        log.info("receive actionad rate");
        RateAction rateAction = mapper.dtoToModel(dto);
        publisher.publishEvent(rateAction);
    }
}
