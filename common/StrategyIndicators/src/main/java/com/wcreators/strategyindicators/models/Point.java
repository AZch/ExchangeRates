package com.wcreators.strategyindicators.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Point {
    private Date time;
    private double value;
}