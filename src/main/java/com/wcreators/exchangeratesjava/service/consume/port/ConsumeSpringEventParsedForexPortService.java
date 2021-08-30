package com.wcreators.exchangeratesjava.service.consume.port;

import com.wcreators.exchangeratesjava.event.SpringRateEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("consumeSpringEventParsedForexPortService")
public class ConsumeSpringEventParsedForexPortService implements ConsumePortService<SpringRateEvent, SpringRateEvent> {

    @Override
    public SpringRateEvent receiveRate(SpringRateEvent event) {
        return event;
    }
}
