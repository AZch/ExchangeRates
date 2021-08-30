package com.wcreators.exchangeratesjava.service.process.logic.strategy;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
@RequiredArgsConstructor
public class StrategyOne {

    private final Cup cup;
    private final EMA ema;
    private final RSI rsi;

    public Optional<Rate> addRate(Rate rate) {
        if (isNotRateForStrategy(rate)) {
            return Optional.empty();
        }

        cup.addRate(rate);
        ema.addRate(rate);
        rsi.addRate(rate);

        return Optional.of(rate);
    }

    private boolean isRateForStrategy(Rate rate) {
        return (rate.getMajor().equals("USD") && rate.getMinor().equals("EUR"))
                || (rate.getMajor().equals("EUR") && rate.getMinor().equals("USD"));
    }

    private boolean isNotRateForStrategy(Rate rate) {
        return !isRateForStrategy(rate);
    }

}

@Service
@RequiredArgsConstructor
class RSI {

    private final DateUtils dateUtils;
    private final List<Elem> elems = new LinkedList<>();
    private Elem current;

    public int getElemsSize() {
        return elems.size();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
            .build();

    public void addRate(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getTime()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }

    @Builder
    @Getter
    public static class Elem {

        private Date time;

        public void addPrice(double price, Date current) {

        }
    }
}

@Service
@RequiredArgsConstructor
class EMA {

    private final DateUtils dateUtils;
    private final List<Elem> elems = new LinkedList<>();
    private Elem current;

    public int getElemsSize() {
        return elems.size();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
            .sum(rate.getSell())
            .count(1)
            .time(rate.getCreatedDate())
            .build();

    public void addRate(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getTime()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }

    @Builder
    @Getter
    public static class Elem {
        private Date time;
        private double sum;
        private int count;

        public double getPoint() {
            return sum / count;
        }

        private void addPrice(double price, Date current) {
            double alpha = getAlpha(count + 1);
            sum = (price * alpha) + sum * (1 - alpha);
            count++;
            time = current;
        }

        private double getAlpha(double N) {
            return 2 / (1 + N);
        }
    }
}

@Service
@RequiredArgsConstructor
class Cup {

    private final List<Elem> elems = new LinkedList<>();
    private Elem current;
    private final DateUtils dateUtils;

    public int getElemsSize() {
        return elems.size();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
            .start(rate.getCreatedDate())
            .high(rate.getSell())
            .low(rate.getSell())
            .open(rate.getSell())
            .close(rate.getSell())
            .end(rate.getCreatedDate())
            .build();

    public void addRate(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getStart()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }

    @Builder
    @Getter
    public static class Elem {

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
    }
}
