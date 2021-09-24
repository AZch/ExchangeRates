package com.wcreators.exchangeratesjava.service.consume.port;

import com.wcreators.exchangeratesjava.event.SpringActionableRateEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("consumeSpringEventActionRatePortService")
public class ConsumeSpringEventActionRatePortService implements ConsumePortService<SpringActionableRateEvent, SpringActionableRateEvent> {
    @Override
    public SpringActionableRateEvent receiveRate(SpringActionableRateEvent event) {
        return event;
    }
}
