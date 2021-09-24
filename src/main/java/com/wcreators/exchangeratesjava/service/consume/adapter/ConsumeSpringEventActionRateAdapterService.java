package com.wcreators.exchangeratesjava.service.consume.adapter;

import com.wcreators.exchangeratesjava.event.SpringActionableRateEvent;
import com.wcreators.exchangeratesjava.service.consume.logic.db.action.ConsumeActionRateService;
import com.wcreators.exchangeratesjava.service.consume.port.ConsumePortService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumeSpringEventActionRateAdapterService implements ConsumeAdapterService<SpringActionableRateEvent> {

    private final ConsumeActionRateService consume;

    private final ConsumePortService<SpringActionableRateEvent, SpringActionableRateEvent> port;

    @Override
    @EventListener
    public void adaptConsume(SpringActionableRateEvent event) {
        SpringActionableRateEvent portedEvent = port.receiveRate(event);
        consume.consume(portedEvent.getRate(), portedEvent.getAction());
    }
}
