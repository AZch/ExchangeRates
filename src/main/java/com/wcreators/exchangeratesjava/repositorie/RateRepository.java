package com.wcreators.exchangeratesjava.repositorie;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<RateEntity, Long> {

    boolean existsByMajorAndMinor(String major, String minor);

    List<RateEntity> findAllByMajorAndMinorAndResource(String major, String minor, Resource resource);
}
