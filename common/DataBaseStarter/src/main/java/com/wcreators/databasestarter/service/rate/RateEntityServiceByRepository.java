package com.wcreators.databasestarter.service.rate;

import com.wcreators.databasestarter.entity.RateEntity;
import com.wcreators.databasestarter.mapper.RateAndEntityMapper;
import com.wcreators.databasestarter.repository.rate.RateRepository;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateEntityServiceByRepository implements RateEntityService {

    private final RateRepository repository;
    private final RateAndEntityMapper mapper;

    @Override
    public void save(Rate rate, Resource resource) {
        RateEntity entity = mapper.modelToEntity(rate, resource);
        repository.save(entity);
    }
}
