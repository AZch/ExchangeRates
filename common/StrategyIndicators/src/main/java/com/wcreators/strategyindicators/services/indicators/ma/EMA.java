package com.wcreators.strategyindicators.services.indicators.ma;


import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.storage.StorageIndicator;
import lombok.Getter;

public class EMA extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private final SMA sma;
    private final Decimal multiplier;

    public EMA(int period) {
        this.period = period;
        this.sma = new SMA(period);
        // 2 / (period + 1)
        this.multiplier = Decimal.TWO.divide(Decimal.valueOf(period).plus(Decimal.ONE));
    }

    @Override
    public Decimal calculate(Decimal value) {
        if (isEmpty()) {
            return value;
        }
        // multiplier * value + (1 - multiplier) * prevValue
        Decimal v1 = Decimal.valueOf(multiplier.doubleValue() * value.doubleValue());
        double v2 = (1 - multiplier.doubleValue()) * lastAdded().doubleValue();
        return Decimal.valueOf(multiplier.doubleValue() * value.doubleValue() + (1 - multiplier.doubleValue()) * lastAdded().doubleValue());
//        return multiplier
//                .multiply(value)
//                .plus(
//                        Decimal.ONE
//                                .minus(multiplier)
//                                .multiply(prevValue())
//                );
    }
}
