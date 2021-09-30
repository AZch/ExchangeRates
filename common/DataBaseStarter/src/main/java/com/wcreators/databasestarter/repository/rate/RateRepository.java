package com.wcreators.databasestarter.repository.rate;

import com.wcreators.databasestarter.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateEntity, Long> {
}
