package com.wcreators.kafkastarter.topics.cup.parsed;

import com.wcreators.kafkastarter.dto.CupRateDTO;
import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.kafkastarter.mappers.Mapper;
import com.wcreators.kafkastarter.mappers.RateToDtoMapper;
import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.kafka.topics.parsed-cup-eur-usd.producer", havingValue = "true")
@RequiredArgsConstructor
public class ParsedCupRateProducerService implements ProducerService<CupRatePoint> {

    private final KafkaTemplate<String, CupRateDTO> template;
    private final Mapper<CupRatePoint, CupRateDTO> mapper;

    @Override
    public String topicName(CupRatePoint model) {
        return String.format("parsed.CUP-%s-%s", model.getMajor(), model.getMinor());
    }

    @Override
    public void produce(CupRatePoint model) {
        CupRateDTO dto = mapper.modelToDto(model);
        template.send(topicName(model), dto);
    }
}
