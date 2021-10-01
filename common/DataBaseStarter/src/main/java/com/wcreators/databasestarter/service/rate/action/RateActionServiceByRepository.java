package com.wcreators.databasestarter.service.rate.action;

import com.wcreators.databasestarter.entity.RateActionEntity;
import com.wcreators.databasestarter.mapper.RateActionAndEntityMapper;
import com.wcreators.databasestarter.repository.rate.RateActionRepository;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateActionServiceByRepository implements RateActionService {

    private final RateActionRepository repository;
    private final RateActionAndEntityMapper mapper;

    @Override
    public void save(RateAction rateAction) {
        RateActionEntity entity = mapper.modelToEntity(rateAction);
        repository.save(entity);
    }
}
