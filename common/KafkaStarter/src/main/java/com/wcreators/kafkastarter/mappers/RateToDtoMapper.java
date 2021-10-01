package com.wcreators.kafkastarter.mappers;

import com.wcreators.kafkastarter.dto.RateDTO;
import com.wcreators.objectmodels.model.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateToDtoMapper {

    public RateDTO modelToDto(Rate rate) {
        return RateDTO.builder()
                .major(rate.getMajor())
                .minor(rate.getMinor())
                .sell(rate.getSell())
                .buy(rate.getBuy())
                .date(rate.getCreatedDate())
                .build();
    }

    public Rate dtoToModel(RateDTO dto) {
        return Rate.builder()
                .major(dto.getMajor())
                .minor(dto.getMinor())
                .buy(dto.getBuy())
                .sell(dto.getSell())
                .createdDate(dto.getDate())
                .build();
    }
}
