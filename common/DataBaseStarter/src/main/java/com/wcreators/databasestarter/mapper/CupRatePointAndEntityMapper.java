package com.wcreators.databasestarter.mapper;

import com.wcreators.databasestarter.entity.CupRatePointEntity;
import com.wcreators.databasestarter.entity.RateActionEntity;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.RateAction;
import org.springframework.stereotype.Component;

@Component
public class CupRatePointAndEntityMapper {

    public CupRatePointEntity modelToEntity(CupRatePoint model) {
        return CupRatePointEntity.builder()
                .major(model.getMajor())
                .minor(model.getMinor())
                .high(model.getHigh())
                .low(model.getLow())
                .open(model.getOpen())
                .close(model.getClose())
                .start(model.getStart())
                .end(model.getEnd())
                .build();
    }

    public RateAction entityToModel(RateActionEntity entity) {
        return null;
    }
}
