package com.wcreators.databasestarter.service.rate;

import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.model.Rate;

public interface RateEntityService {
    void save(Rate rate, Resource resource);
}
