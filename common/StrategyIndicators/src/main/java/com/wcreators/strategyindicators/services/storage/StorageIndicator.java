package com.wcreators.strategyindicators.services.storage;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class StorageIndicator<T, E> {

    protected final List<E> points = new ArrayList<>();

    public E addPoint(T value) {
        E point = calculate(value);
        points.add(point);
        return point;
    }

    protected abstract E calculate(T value);

    protected boolean isEmpty() {
        return points.isEmpty();
    }

    public E lastAdded() {
        return points.get(points.size() - 1);
    }

    public int size() {
        return points.size();
    }

    public E get(int index) {
        return points.get(index);
    }

}
