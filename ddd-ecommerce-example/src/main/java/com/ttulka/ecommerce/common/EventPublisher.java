package com.ttulka.ecommerce.common;

/**
 * <p>Publisher for domain events.</p>
 * <p>Decouples the domain layer from the messaging implementation.</p>
 */
public interface EventPublisher {

    /**
     * Raises a domain event.
     *
     * @param event the domain event.
     */
    void raise(Object event);
}
