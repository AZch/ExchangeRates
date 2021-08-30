package com.wcreators.exchangeratesjava.service.consume.adapter;

import com.wcreators.exchangeratesjava.event.SpringRateEvent;
import com.wcreators.exchangeratesjava.service.consume.logic.ConsumeService;
import com.wcreators.exchangeratesjava.service.consume.port.ConsumePortService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumeSpringEventParsedForexAdapterService implements ConsumeAdapterService<SpringRateEvent> {

    @Qualifier("consumeStoreDBService")
    private final ConsumeService consume;

    @Qualifier("consumeSpringEventParsedForexPortService")
    private final ConsumePortService<SpringRateEvent, SpringRateEvent> port;

    @Override
    @EventListener
    public void adaptConsume(SpringRateEvent event) {
        SpringRateEvent portedEvent = port.receiveRate(event);
        consume.consume(portedEvent.getRate(), portedEvent.getResource());
    }
}
