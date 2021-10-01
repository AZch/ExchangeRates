package com.wcreators.kafkastarter.mappers;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateActionToDtoMapper {

    private final RateToDtoMapper mapper;

    public RateActionDTO modelToDto(RateAction rateAction) {
        return RateActionDTO.builder()
                .dto(mapper.modelToDto(rateAction.getRate()))
                .action(rateAction.getAction())
                .build();
    }

    public RateAction dtoToModel(RateActionDTO dto) {
        return RateAction.builder()
                .rate(mapper.dtoToModel(dto.getDto()))
                .action(dto.getAction())
                .build();
    }
}
