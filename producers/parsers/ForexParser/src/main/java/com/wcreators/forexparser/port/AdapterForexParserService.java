package com.wcreators.forexparser.port;

import com.wcreators.forexparser.parser.ParseRateService;
import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.services.cup.Cup;
import com.wcreators.utils.date.DateUtilsUtilDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdapterForexParserService implements AdapterService {

    private final ParseRateService parseRateService;

    private final ProducerService<CupRatePoint> producerService;
    
    private final Cup cup = new Cup(new DateUtilsUtilDate());

    @Override
    @Scheduled(fixedDelay = 700)
    public void adaptEvent() {
        List<Rate> rates = parseRates();
        rates.stream()
                .filter(rate -> rate.getName().equals("EUR/USD"))
                .map(cup::addPoint)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(point -> log.debug("Parsed cup rate {}", point))
                .map(point -> CupRatePoint.builder()
                        .major("EUR")
                        .minor("USD")
                        .start(point.getStart())
                        .end(point.getEnd())
                        .open(point.getOpen())
                        .close(point.getClose())
                        .high(point.getHigh())
                        .low(point.getLow())
                    .build())
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
