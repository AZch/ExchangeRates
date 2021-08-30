package com.wcreators.exchangeratesjava.service.supplier.adapter;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.supplier.logic.parse.ParseRateService;
import com.wcreators.exchangeratesjava.service.supplier.port.SupplierPortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpringForexEventSupplierAdapterService implements SupplierAdapterService {

    @Qualifier("parseForexRateServiceSelenium")
    private final ParseRateService parseRate;

    @Qualifier("springEventSupplierPortService")
    private final SupplierPortService supplier;

    @Override
    @Scheduled(fixedDelay = 100)
    public void adaptEvent() {
        List<Rate> rates = parseRates();
        supplier.sendParsedRates(rates, parseRate.getResource(), parseRate.getResourceAction());
    }

    private List<Rate> parseRates() {
        try {
            return parseRate.parse();
        } catch (Exception e) {
            log.warn("Exception when parsing {} {}", parseRate.getResource().getName(), e.getMessage());
            parseRate.reload();
        }
        return Collections.emptyList();
    }
}
