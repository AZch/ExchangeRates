package com.wcreators.forexparser.port;

import com.wcreators.kafkastarter.producer.Producer;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.constant.ResourceAction;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPortService implements PortService {

    private final KafkaTemplate<Long, Rate> parsedRateTemplate;
//    private final Producer producer;

    @Override
    public void sendParsedRates(List<Rate> rates, Resource resource, ResourceAction resourceAction) {
        log.info("<= sending {}", rates);
//        producer.getConfig();
//        rates.stream()
//                .filter(rate -> rate.getName().equals("EUR/USD"))
//                .forEach(rate -> parsedRateTemplate.send(String.format("rate.%s", rate.getName()), rate));

    }
}
