package com.wcreators.databasestarter.service.cup;

import com.wcreators.databasestarter.entity.CupRatePointEntity;
import com.wcreators.databasestarter.mapper.CupRatePointAndEntityMapper;
import com.wcreators.databasestarter.repository.cup.CupRatePointRepository;
import com.wcreators.objectmodels.model.CupRatePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CupRatePointServiceByRepository implements CupRatePointService {

    private final CupRatePointRepository repository;
    private final CupRatePointAndEntityMapper mapper;

    @Override
    public void save(CupRatePoint cupRatePoint) {
        CupRatePointEntity entity = mapper.modelToEntity(cupRatePoint);
        repository.save(entity);
    }
}
