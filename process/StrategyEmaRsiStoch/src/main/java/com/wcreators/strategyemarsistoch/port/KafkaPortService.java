package com.wcreators.strategyemarsistoch.port;

import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import com.wcreators.strategyemarsistoch.strategy.ProcessRatesService;
import com.wcreators.strategyindicators.models.CupPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void receive(CupRatePoint cupRatePoint) {
        CupPoint point = CupPoint.builder()
                .high(cupRatePoint.getHigh())
                .low(cupRatePoint.getLow())
                .open(cupRatePoint.getOpen())
                .close(cupRatePoint.getClose())
                .start(cupRatePoint.getStart())
                .end(cupRatePoint.getEnd())
                .build();
        String name = cupRatePoint.getMajor() + "/" + cupRatePoint.getMinor();
        Optional<String> optionalAction = processRatesService.addRate(point);
        optionalAction.ifPresentOrElse(
                action -> {
                    log.info("Send action {}: {}", name, action);
                    send(RateAction.builder()
                            .major(cupRatePoint.getMajor())
                            .minor(cupRatePoint.getMinor())
                            .created(new Date())
                            .action(action)
                            .rate(cupRatePoint.getClose())
                            .strategy(processRatesService.getStrategy())
                            .build());
                },
                () -> log.info("Nothing actions for rate {}", cupRatePoint)
        );
    }
}
