package com.wcreators.kafkastarter.mappers;

import com.wcreators.kafkastarter.dto.CupRateDTO;
import com.wcreators.objectmodels.model.CupRatePoint;
import org.springframework.stereotype.Service;

@Service
public class CupRateToDtoMapper implements Mapper<CupRatePoint, CupRateDTO> {

    @Override
    public CupRateDTO modelToDto(CupRatePoint model) {
        return CupRateDTO.builder()
                .major(model.getMajor())
                .minor(model.getMinor())
                .high(model.getHigh())
                .low(model.getLow())
                .open(model.getOpen())
                .close(model.getClose())
                .start(model.getStart())
                .end(model.getEnd())
                .build();
    }

    @Override
    public CupRatePoint dtoToModel(CupRateDTO dto) {
        return CupRatePoint.builder()
                .major(dto.getMajor())
                .minor(dto.getMinor())
                .high(dto.getHigh())
                .low(dto.getLow())
                .open(dto.getOpen())
                .close(dto.getClose())
                .start(dto.getStart())
                .end(dto.getEnd())
                .build();
    }
}
