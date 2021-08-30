package com.wcreators.exchangeratesjava.service.database.rate;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.entity.RateEntity;
import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface RateEntityService {
    List<RateEntity> saveAll(List<Rate> rate, Resource resource);

    RateEntity save(Rate rate, Resource resource);

    List<RateEntity> getRatesByPair(String first, String second, Resource resource);
}
