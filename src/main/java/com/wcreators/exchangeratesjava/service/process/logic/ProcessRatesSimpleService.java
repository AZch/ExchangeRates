package com.wcreators.exchangeratesjava.service.process.logic;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.pnl.GrossReturnCriterion;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.PriceIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
@Qualifier("processRatesSimpleService")
@Slf4j
@RequiredArgsConstructor
public class ProcessRatesSimpleService implements ProcessRatesService {

    private BarSeries series;
    private TradingRecord tradingRecord;
    private Strategy strategy;

    private final BarTemporaryDataCollector collector = new BarTemporaryDataCollector();
    private final DateUtils dateUtils;

    @PostConstruct
    public void init() {
        this.series = new BaseBarSeries();
        PriceIndicator closePrice = new ClosePriceIndicator(series);
        RSIIndicator rsi = new RSIIndicator(closePrice, 3);
        EMAIndicator ema = new EMAIndicator(closePrice, 7);
        StochasticOscillatorKIndicator stochasticK = new StochasticOscillatorKIndicator(series, 3);
        StochasticOscillatorDIndicator stochasticD = new StochasticOscillatorDIndicator(stochasticK);

        // Entry rule
        // The long-term trend is up when a security is above its 200-period SMA.
        Rule entryRule = new OverIndicatorRule(rsi, ema) // Trend
                .and(new CrossedDownIndicatorRule(rsi, 5)) // Signal 1
                .and(new OverIndicatorRule(ema, closePrice)); // Signal 2

        // Exit rule
        // The long-term trend is down when a security is below its 200-period SMA.
        Rule exitRule = new UnderIndicatorRule(stochasticK, rsi) // Trend
                .and(new CrossedUpIndicatorRule(rsi, 95)) // Signal 1
                .and(new UnderIndicatorRule(ema, closePrice)); // Signal 2

        this.strategy = new BaseStrategy(entryRule, exitRule);

        // Running the strategy
//        BarSeriesManager seriesManager = new BarSeriesManager(series);
//
//        this.tradingRecord = seriesManager.run(strategy);
    }

    @Override
    public List<Rate> process(List<Rate> rates) {

        Optional<Rate> usdEurRate = rates.stream()
                .filter(rate ->
                        (rate.getMajor().equals("USD") && rate.getMinor().equals("EUR"))
                                || (rate.getMajor().equals("EUR") && rate.getMinor().equals("USD"))
                ).findFirst();
        if (usdEurRate.isEmpty()) {
            log.warn("Not found eur/usd pair");
            return rates;
        }

        Optional<Bar> bar = collector.collect(usdEurRate.get(), dateUtils);
        bar.ifPresent(a -> log.info("value added"));
        bar.ifPresent(value -> series.addBar(value));


        BarSeriesManager seriesManager = new BarSeriesManager(series);

        this.tradingRecord = seriesManager.run(this.strategy);

        log.info("Number of positions for the strategy: {}, {}", tradingRecord.getPositionCount(), series.getBarCount());
//
        // Analysis
        log.info("Total return for the strategy: {}", new GrossReturnCriterion().calculate(series, tradingRecord));

        return rates;
    }
}

class BarTemporaryDataCollector {

    private double high;
    private double low;
    private double open;
    private double close;
    private Date start;
    private Date end;

    public Optional<Bar> collect(Rate rate, DateUtils dateUtils) {
        Optional<Bar> response = Optional.empty();

        if (start == null) {
            start = rate.getCreatedDate();
            end = rate.getCreatedDate();
            open = low = high = close = rate.getSell();
        }

        if (dateUtils.getMinutes(start) == dateUtils.getMinutes(rate.getCreatedDate())) {
            close = rate.getSell();
            end = rate.getCreatedDate();
            if (close > high) {
                high = close;
            }
            if (close < low) {
                low = close;
            }
        } else {
            Duration duration = Duration.ofMinutes(1);
            Bar bar = new BaseBar(
                    duration,
                    ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")),
                    BigDecimal.valueOf(open),
                    BigDecimal.valueOf(high),
                    BigDecimal.valueOf(low),
                    BigDecimal.valueOf(close),
                    BigDecimal.ZERO
            );
            response = Optional.of(bar);
            start = rate.getCreatedDate();
            open = low = high = close = rate.getSell();
        }
        return response;
    }
}
