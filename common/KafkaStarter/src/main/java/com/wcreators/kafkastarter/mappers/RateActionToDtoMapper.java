package com.wcreators.kafkastarter.mappers;

import com.wcreators.kafkastarter.dto.RateActionDTO;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateActionToDtoMapper implements Mapper<RateAction, RateActionDTO> {

    private final RateToDtoMapper mapper;

    @Override
    public RateActionDTO modelToDto(RateAction rateAction) {
        return RateActionDTO.builder()
                .major(rateAction.getMajor())
                .minor(rateAction.getMinor())
                .created(rateAction.getCreated())
                .action(rateAction.getAction())
                .build();
    }

    @Override
    public RateAction dtoToModel(RateActionDTO dto) {
        return RateAction.builder()
                .major(dto.getMajor())
                .minor(dto.getMinor())
                .created(dto.getCreated())
                .action(dto.getAction())
                .build();
    }
}
