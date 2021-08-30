package com.wcreators.exchangeratesjava.service.supplier.port;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.constant.ResourceAction;
import com.wcreators.exchangeratesjava.event.SpringRatesEvent;
import com.wcreators.exchangeratesjava.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("springEventSupplierPortService")
public class SpringEventSupplierPortService implements SupplierPortService {

    private final ApplicationEventPublisher publisher;

    @Override
    public void sendParsedRates(List<Rate> rates, Resource resource, ResourceAction resourceAction) {
        SpringRatesEvent event = SpringRatesEvent.builder()
                .rates(rates)
                .resource(resource)
                .resourceAction(resourceAction)
                .build();
        publisher.publishEvent(event);
    }
}
