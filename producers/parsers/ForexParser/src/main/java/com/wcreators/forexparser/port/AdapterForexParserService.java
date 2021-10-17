package com.wcreators.forexparser.port;

import com.wcreators.forexparser.parser.ParseRateService;
import com.wcreators.kafkastarter.topics.ProducerService;
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

    private final ProducerService<Rate> producerService;

    @Override
    @Scheduled(fixedDelay = 100)
    public void adaptEvent() {
        List<Rate> rates = parseRates();
        rates.stream()
                .filter(rate -> rate.getName().equals("EUR/USD"))
                .forEach(producerService::produce);
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
