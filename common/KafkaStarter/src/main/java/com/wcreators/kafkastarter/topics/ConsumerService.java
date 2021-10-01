package com.wcreators.kafkastarter.topics;

public interface ConsumerService<T> {
    void consume(T dto);
}
