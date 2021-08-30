package com.wcreators.exchangeratesjava.mapper;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.entity.RateEntity;
import com.wcreators.exchangeratesjava.model.Rate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RateAndEntityMapper {

    public RateEntity rateToEntity(Rate rate, Resource resource) {
        return RateEntity.builder()
                .major(rate.getMajor())
                .minor(rate.getMinor())
                .sell(rate.getSell())
                .buy(rate.getBuy())
                .createdDate(rate.getCreatedDate())
                .resource(resource.getName())
                .build();
    }

    public Rate entityToRate(RateEntity entity) {
        return Rate.builder()
                .major(entity.getMajor())
                .minor(entity.getMinor())
                .sell(entity.getSell())
                .buy(entity.getBuy())
                .createdDate(entity.getCreatedDate())
                .build();
    }

}
