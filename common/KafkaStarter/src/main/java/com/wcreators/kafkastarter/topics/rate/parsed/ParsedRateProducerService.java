package com.wcreators.kafkastarter.topics.rate.parsed;

import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.mappers.RateToDtoMapper;
import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParsedRateProducerService implements ProducerService<Rate> {

    private final KafkaTemplate<String, RateDTO> template;
    private final RateToDtoMapper mapper;

    @Override
    public String topicName(Rate model) {
        return String.format("parsed.%s-%s", model.getMajor(), model.getMinor());
    }

    @Override
    public void produce(Rate model) {
        RateDTO dto = mapper.modelToDto(model);
        template.send(topicName(model), dto);
    }
}
