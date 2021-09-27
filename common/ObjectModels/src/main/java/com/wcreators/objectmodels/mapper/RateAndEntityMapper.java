package com.wcreators.objectmodels.mapper;

import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.entity.RateEntity;
import com.wcreators.objectmodels.model.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateAndEntityMapper {

    public RateEntity modelToEntity(Rate model, Resource resource) {
        return RateEntity.builder()
                .major(model.getMajor())
                .minor(model.getMinor())
                .sell(model.getSell())
                .buy(model.getBuy())
                .createdDate(model.getCreatedDate())
                .resource(resource.getName())
                .build();
    }

    public Rate entityToModel(RateEntity entity) {
        return Rate.builder()
                .major(entity.getMajor())
                .minor(entity.getMinor())
                .buy(entity.getBuy())
                .sell(entity.getSell())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
