package com.wcreators.exchangeratesjava.service.process.logic.curve;

import com.wcreators.exchangeratesjava.model.CupCurvePoint;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

@RequiredArgsConstructor
@Service
public class CupCurveBuilder implements CurveBuilder<CupCurvePoint> {

    private final Map<String, List<CupCurvePoint>> curves = new HashMap<>();
    private final DateUtils dateUtils;


    public boolean isUpdateCurvePoint(CupCurvePoint lastPoint, Rate rate) {
        return dateUtils.getMinutes(lastPoint.getTime()) == dateUtils.getMinutes(rate.getCreatedDate());
    }

    @Override
    public Map<String, List<CupCurvePoint>> build(List<Rate> rates) {
        return build(curves, rates);
    }

    @Override
    public CupCurvePoint createFromRate(Rate rate) {
        return CupCurvePoint.builder()
                .max(rate.getSell())
                .min(rate.getSell())
                .open(rate.getSell())
                .close(rate.getSell())
                .time(rate.getCreatedDate())
                .build();
    }

    @Override
    public CupCurvePoint updateFromRate(CupCurvePoint lastPoint, Rate rate) {
        return CupCurvePoint.builder()
                .max(max(lastPoint.getMax(), rate.getSell()))
                .min(min(lastPoint.getMin(), rate.getSell()))
                .open(lastPoint.getOpen())
                .close(lastPoint.getClose())
                .time(rate.getCreatedDate())
                .build();
    }
}
