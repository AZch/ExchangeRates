package com.wcreators.exchangeratesjava.service.consume.logic.db.action;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.entity.RateActionEntity;
import com.wcreators.exchangeratesjava.mapper.RateAndEntityMapper;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.repositorie.RateActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumeActionRateStoreDBService implements ConsumeActionRateService {

    private final RateActionRepository repository;

    private final RateAndEntityMapper mapper;

    @Override
    public void consume(Rate rate, String action) {
        repository.save(
                RateActionEntity.builder()
                        .buy(rate.getBuy())
                        .sell(rate.getSell())
                        .major(rate.getMajor())
                        .minor(rate.getMinor())
                        .createdDate(rate.getCreatedDate())
                        .action(action)
                        .build()
        );
    }
}
