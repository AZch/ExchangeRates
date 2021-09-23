package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Point {
    private Date time;
    private double value;
}