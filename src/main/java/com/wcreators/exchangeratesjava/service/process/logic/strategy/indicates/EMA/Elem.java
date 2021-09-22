package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Elem {
    private Date time;
    private double sum;
}