package com.wcreators.exchangeratesjava.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Builder
public class CupCurvePoint {
    private final Double max;
    private final Double min;
    private final Double open;
    private final Double close;
    private final Date time;
}
