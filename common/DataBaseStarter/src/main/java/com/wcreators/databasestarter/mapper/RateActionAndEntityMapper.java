package com.wcreators.databasestarter.mapper;

import com.wcreators.databasestarter.entity.RateActionEntity;
import com.wcreators.objectmodels.model.RateAction;
import org.springframework.stereotype.Component;

@Component
public class RateActionAndEntityMapper{

    public RateActionEntity modelToEntity(RateAction model) {
        return RateActionEntity.builder()
                .major(model.getMajor())
                .minor(model.getMinor())
                .date(model.getCreated())
                .action(model.getAction())
                .strategy(model.getStrategy().getType())
                .rate(model.getRate())
                .build();
    }

    public RateAction entityToModel(RateActionEntity entity) {
        return null;
    }
}
