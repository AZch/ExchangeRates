package com.wcreators.objectmodels.mapper;

import com.wcreators.objectmodels.entity.RateActionEntity;
import com.wcreators.objectmodels.model.RateAction;
import org.springframework.stereotype.Component;

@Component
public class RateActionAndEntityMapper{

    public RateActionEntity modelToEntity(RateAction model) {
        return RateActionEntity.builder()
                .major(model.getRate().getMajor())
                .minor(model.getRate().getMinor())
                .sell(model.getRate().getSell())
                .buy(model.getRate().getBuy())
                .createdDate(model.getRate().getCreatedDate())
                .action(model.getAction())
                .build();
    }

    public RateAction entityToModel(RateActionEntity entity) {
        return null;
    }
}
