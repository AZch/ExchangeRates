package com.wcreators.kafkastarter.topics.rate.action;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.kafkastarter.mappers.RateActionToDtoMapper;
import com.wcreators.kafkastarter.topics.ConsumerService;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateActionConsumerService implements ConsumerService<RateActionDTO> {

    private final RateActionToDtoMapper mapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @KafkaListener(id = "ActionRate", topics = {"action.EUR-USD"}, containerFactory = "singleActionRateConsumerFactory")
    public void consume(RateActionDTO dto) {
        RateAction rateAction = mapper.dtoToModel(dto);
        publisher.publishEvent(rateAction);
    }
}
