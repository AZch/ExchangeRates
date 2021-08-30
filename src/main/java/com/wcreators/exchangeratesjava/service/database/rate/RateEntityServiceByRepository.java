package com.wcreators.exchangeratesjava.service.database.rate;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.entity.RateEntity;
import com.wcreators.exchangeratesjava.mapper.RateAndEntityMapper;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.repositorie.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateEntityServiceByRepository implements RateEntityService {

    private final RateRepository rateRepository;
    private final RateAndEntityMapper mapper;

    @Override
    public List<RateEntity> saveAll(List<Rate> rates, Resource resource) {
        return rateRepository.saveAll(
                rates.stream()
                        .map(rate -> mapper.rateToEntity(rate, resource))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public RateEntity save(Rate rate, Resource resource) {
        return rateRepository.save(mapper.rateToEntity(rate, resource));
    }

    public List<RateEntity> getRatesByPair(String first, String second, Resource resource) {
        if (rateRepository.existsByMajorAndMinor(first, second)) {
            return rateRepository.findAllByMajorAndMinorAndResource(first, second, resource);
        }
        if (rateRepository.existsByMajorAndMinor(second, first)) {
            return rateRepository.findAllByMajorAndMinorAndResource(second, first, resource);
        }
        return Collections.emptyList();
    }
}
