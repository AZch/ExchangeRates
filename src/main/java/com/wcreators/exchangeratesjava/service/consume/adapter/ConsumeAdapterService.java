package com.wcreators.exchangeratesjava.service.consume.adapter;

public interface ConsumeAdapterService<T> {
    void adaptConsume(T event);
}
