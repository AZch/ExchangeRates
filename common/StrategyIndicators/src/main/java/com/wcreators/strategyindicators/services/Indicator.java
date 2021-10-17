package com.wcreators.strategyindicators.services;

public interface Indicator<T, R, U> {
    R addPoint(T point);

    U getUtils();
}
