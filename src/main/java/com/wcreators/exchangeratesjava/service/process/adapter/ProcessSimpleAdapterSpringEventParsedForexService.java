package com.wcreators.exchangeratesjava.service.process.adapter;

import com.wcreators.exchangeratesjava.event.SpringRatesEvent;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.ProcessRatesService;
import com.wcreators.exchangeratesjava.service.process.port.ProcessPortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessSimpleAdapterSpringEventParsedForexService implements ProcessEventAdapterService<SpringRatesEvent> {

    @Qualifier("strategyOne")
    private final ProcessRatesService processRates;

    @Qualifier("springEventParsedForexProcessPortService")
    private final ProcessPortService<SpringRatesEvent> port;

    @Override
    @EventListener
    public void adaptProcess(SpringRatesEvent event) {
        List<Rate> rates = port.receiveRates(event);
        Optional<Rate> usdEurRate = rates.stream().filter(processRates::isRateForStrategy).findFirst();
        if (usdEurRate.isPresent()) {
            Optional<Rate> processedRate = processRates.addRate(usdEurRate.get());
            processedRate.ifPresent(rate -> port.sendAction(List.of(rate)));
        } else {
            log.warn("No have eur/usd pair in income rates");
        }
    }
}
