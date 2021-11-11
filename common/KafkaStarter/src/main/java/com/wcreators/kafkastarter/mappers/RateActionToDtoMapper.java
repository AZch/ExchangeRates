package com.wcreators.kafkastarter.mappers;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.objectmodels.constant.Strategy;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateActionToDtoMapper implements Mapper<RateAction, RateActionDTO> {

    @Override
    public RateActionDTO modelToDto(RateAction rateAction) {
        return RateActionDTO.builder()
                .major(rateAction.getMajor())
                .minor(rateAction.getMinor())
                .created(rateAction.getCreated())
                .action(rateAction.getAction())
                .rate(rateAction.getRate())
                .strategy(rateAction.getStrategy().getType())
                .build();
    }

    @Override
    public RateAction dtoToModel(RateActionDTO dto) {
        Optional<Strategy> optionalStrategy = Strategy.strategyByType(dto.getStrategy());
        if (optionalStrategy.isEmpty()) {
            log.warn("No have strategy type for rate {}", dto);
        }
        return RateAction.builder()
                .major(dto.getMajor())
                .minor(dto.getMinor())
                .created(dto.getCreated())
                .action(dto.getAction())
                .strategy(optionalStrategy.orElse(Strategy.EMPTY))
                .rate(dto.getRate())
                .build();
    }
}
