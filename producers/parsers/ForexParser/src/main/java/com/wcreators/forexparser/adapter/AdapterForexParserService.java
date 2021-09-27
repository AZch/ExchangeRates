package com.wcreators.forexparser.adapter;

import com.wcreators.forexparser.parser.ParseRateService;
import com.wcreators.forexparser.port.PortService;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdapterForexParserService implements AdapterService {

    private final ParseRateService parseRateService;

    private final PortService portService;

    @Override
    @Scheduled(fixedDelay = 100)
    public void adaptEvent() {
        List<Rate> rates = parseRates();
        portService.sendParsedRates(rates, parseRateService.getResource(), parseRateService.getResourceAction());
    }

    private List<Rate> parseRates() {
        try {
            return parseRateService.parse();
        } catch (Exception e) {
            log.warn("Exception when parsing {} {}", parseRateService.getResource().getName(), e.getMessage());
            parseRateService.reload();
        }
        return Collections.emptyList();
    }
}
