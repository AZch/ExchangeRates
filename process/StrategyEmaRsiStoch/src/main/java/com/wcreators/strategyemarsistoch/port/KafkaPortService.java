package com.wcreators.strategyemarsistoch.port;

import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import com.wcreators.strategyemarsistoch.strategy.ProcessRatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPortService implements PortService {

    private final ProducerService<RateAction> producerService;

    private final ProcessRatesService processRatesService;

    @Override
    public void send(RateAction rateAction) {
        producerService.produce(rateAction);
    }

    @Override
    @EventListener
    public void receive(Rate rate) {
        Optional<RateAction> optionalRateAction = processRatesService.addRate(rate);
        optionalRateAction.ifPresentOrElse(
                rateAction -> {
                    log.info("Send action {}: {}", rateAction.getRate().getName(), rateAction.getAction());
                    send(rateAction);
                },
                () -> log.info("Nothing actions for rate {}", rate)
        );
    }
}
