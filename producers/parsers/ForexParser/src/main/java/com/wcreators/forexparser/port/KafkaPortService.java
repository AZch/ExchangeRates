package com.wcreators.forexparser.port;

import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPortService implements PortService {

    private final ProducerService<Rate> producerService;

    @Override
    public void sendParsedRate(Rate rate) {
        producerService.produce(rate);
    }
}
