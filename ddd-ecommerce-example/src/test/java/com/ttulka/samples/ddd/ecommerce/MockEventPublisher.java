package com.ttulka.samples.ddd.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

public final class MockEventPublisher implements EventPublisher {

    private final List<Object> events = new ArrayList<>();

    @Override
    public void raise(Object event) {
        events.add(event);
    }

    public List<Object> events() {
        return Collections.unmodifiableList(events);
    }
}
