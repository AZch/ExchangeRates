package com.wcreators.exchangeratesjava.repositorie;

import com.wcreators.exchangeratesjava.entity.RateActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateActionRepository extends JpaRepository<RateActionEntity, Long> {
}
