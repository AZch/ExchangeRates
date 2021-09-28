package com.wcreators.forexparser.port;

import com.wcreators.kafkastarter.topics.ProducerService;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.constant.ResourceAction;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPortService implements PortService {

    private final ProducerService<Rate> producerService;

    @Override
    public void sendParsedRates(List<Rate> rates, Resource resource, ResourceAction resourceAction) {
        log.info("<= sending {}", rates.size());
        rates.stream()
                .filter(rate -> rate.getName().equals("EUR/USD"))
                .forEach(producerService::produce);

    }
}
