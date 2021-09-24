package com.wcreators.exchangeratesjava.service.consume.logic.db.log;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.database.rate.RateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumeDBLogStoreDBService implements ConsumeDBLogService {

    private final RateEntityService rateEntityService;

    @Override
    public void consume(List<Rate> rates, Resource resource) {
        rateEntityService.saveAll(rates, resource);
    }
}
