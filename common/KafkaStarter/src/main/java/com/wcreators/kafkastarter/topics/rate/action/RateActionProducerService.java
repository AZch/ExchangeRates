package com.wcreators.kafkastarter.topics.rate.action;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.kafkastarter.mappers.RateActionToDtoMapper;
import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.kafka.topics.action-eur-usd.producer", havingValue = "true")
@RequiredArgsConstructor
public class RateActionProducerService implements ProducerService<RateAction> {

    private final KafkaTemplate<String, RateActionDTO> template;
    private final RateActionToDtoMapper mapper;

    @Override
    public String topicName(RateAction model) {
        return String.format("action.%s-%s", model.getRate().getMajor(), model.getRate().getMinor());
    }

    @Override
    public void produce(RateAction model) {
        RateActionDTO dto = mapper.modelToDto(model);
        template.send(topicName(model), dto);
    }
}
