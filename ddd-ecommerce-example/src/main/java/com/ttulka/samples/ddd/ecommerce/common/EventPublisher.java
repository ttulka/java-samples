package com.ttulka.samples.ddd.ecommerce.common;

public interface EventPublisher {

    void raise(Object event);
}
