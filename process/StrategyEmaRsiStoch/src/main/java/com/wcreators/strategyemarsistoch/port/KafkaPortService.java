package com.wcreators.strategyemarsistoch.port;

import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPortService implements PortService {

    private final ProducerService<RateAction> producerService;

    @Override
    public void send(RateAction rateAction) {
        producerService.produce(rateAction);
    }
}
