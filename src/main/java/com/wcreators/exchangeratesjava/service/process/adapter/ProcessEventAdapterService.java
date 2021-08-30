package com.wcreators.exchangeratesjava.service.process.adapter;

public interface ProcessEventAdapterService<T> {
    void adaptProcess(T event);
}
