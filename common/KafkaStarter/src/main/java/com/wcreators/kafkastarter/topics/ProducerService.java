package com.wcreators.kafkastarter.topics;

public interface ProducerService<T> {
    String topicName(T model);

    void produce(T model);
}
