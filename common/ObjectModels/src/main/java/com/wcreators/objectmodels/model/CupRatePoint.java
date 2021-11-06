package com.wcreators.objectmodels.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class CupRatePoint {
    private String major;
    private String minor;
    private double high;
    private double low;
    private double open;
    private double close;
    private Date start;
    private Date end;
}
