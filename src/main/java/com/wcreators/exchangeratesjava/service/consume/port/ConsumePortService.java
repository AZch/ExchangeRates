package com.wcreators.exchangeratesjava.service.consume.port;

public interface ConsumePortService<T, U> {
    U receiveRate(T event);
}
