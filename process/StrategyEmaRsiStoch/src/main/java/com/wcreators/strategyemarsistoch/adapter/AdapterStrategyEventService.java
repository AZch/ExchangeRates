package com.wcreators.strategyemarsistoch.adapter;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import com.wcreators.strategyemarsistoch.logic.strategy.ProcessRatesService;
import com.wcreators.strategyemarsistoch.port.PortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdapterStrategyEventService implements AdapterService {

    private final ProcessRatesService processRatesService;

    private final PortService portService;

    @Override
    @EventListener
    public void adaptEvent(Rate rate) {
        Optional<RateAction> optionalRateAction = processRatesService.addRate(rate);
        optionalRateAction.ifPresentOrElse(
                rateAction -> {
                    log.info("Send action {}", rateAction);
                    portService.send(rateAction);
                },
                () -> log.info("Nothing actions for rate {}", rate)
        );
    }
}
