package com.wcreators.exchangeratesjava.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class MACurvePoint {
    private final Date time;
    private final Double sum;
    private final int count;

    public Double getPoint() {
        return sum / count;
    }
}
