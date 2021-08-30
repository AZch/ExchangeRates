package com.wcreators.exchangeratesjava.service.consume.logic;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.database.rate.RateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("consumeStoreDBService")
public class ConsumeStoreDBService implements ConsumeService {

    private final RateEntityService rateEntityService;

    @Override
    public void consume(List<Rate> rates, Resource resource) {
        rateEntityService.saveAll(rates, resource);
    }
}
