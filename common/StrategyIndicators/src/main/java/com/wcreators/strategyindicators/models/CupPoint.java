package com.wcreators.strategyindicators.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Builder
@Getter
@ToString
public class CupPoint {

    private double high;
    private double low;
    private double open;
    private double close;
    private Date start;
    private Date end;

    public void addPrice(double price, Date current) {
        close = price;
        end = current;
        high = max(high, close);
        low = min(low, close);
    }

    public Decimal getDecimalClose() {
        return Decimal.valueOf(close);
    }

    public Decimal getDecimalHigh() {
        return Decimal.valueOf(high);
    }

    public Decimal getDecimalLow() {
        return Decimal.valueOf(low);
    }
}
