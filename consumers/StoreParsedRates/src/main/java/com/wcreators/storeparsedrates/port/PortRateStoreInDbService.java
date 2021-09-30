package com.wcreators.storeparsedrates.port;

import com.wcreators.databasestarter.service.rate.RateEntityService;
import com.wcreators.databasestarter.service.rate.action.RateActionService;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortRateStoreInDbService {

    private final RateEntityService rateEntityService;

    private final RateActionService rateActionService;

    @EventListener
    public void storeParsedRate(Rate rate) {
        rateEntityService.save(rate, Resource.FOREX);
        log.info("Stored parsed rate {}", rate);
    }

    @EventListener(RateAction.class)
    public void storeActionableRate(RateAction rateAction) {
        rateActionService.save(rateAction);
        log.info("Stored action {} for rate {}", rateAction.getAction(), rateAction.getRate());
    }
}
