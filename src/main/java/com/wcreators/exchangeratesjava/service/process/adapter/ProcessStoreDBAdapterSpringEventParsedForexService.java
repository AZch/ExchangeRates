package com.wcreators.exchangeratesjava.service.process.adapter;

import com.wcreators.exchangeratesjava.event.SpringRatesEvent;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.ProcessRatesService;
import com.wcreators.exchangeratesjava.service.process.port.ProcessPortService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessStoreDBAdapterSpringEventParsedForexService implements ProcessEventAdapterService<SpringRatesEvent> {

    @Qualifier("springEventParsedForexProcessPortService")
    private final ProcessPortService<SpringRatesEvent, List<Rate>> port;

    @Override
    @EventListener
    public void adaptProcess(SpringRatesEvent event) {
        List<Rate> rates = port.receiveRates(event);
        port.sendAction(rates);
    }
}
