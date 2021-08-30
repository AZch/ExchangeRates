package com.wcreators.exchangeratesjava.service.process.port;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.constant.ResourceAction;
import com.wcreators.exchangeratesjava.event.SpringRateEvent;
import com.wcreators.exchangeratesjava.event.SpringRatesEvent;
import com.wcreators.exchangeratesjava.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Qualifier("springEventParsedForexProcessPortService")
@RequiredArgsConstructor
public class SpringEventParsedForexProcessPortService implements ProcessPortService<SpringRatesEvent> {

    private final Resource resource = Resource.FOREX;
    private final ResourceAction action = ResourceAction.PARSE;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<Rate> receiveRates(SpringRatesEvent event) {
        if (event.getResource() == resource && event.getResourceAction() == action) {
            return event.getRates();
        }
        return Collections.emptyList();
    }

    @Override
    public void sendAction(List<Rate> rate) {
        SpringRateEvent event = SpringRateEvent.builder()
                .rate(rate)
                .resource(resource)
                .build();
        publisher.publishEvent(event);
    }
}
