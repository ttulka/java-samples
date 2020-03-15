package com.ttulka.samples.ddd.ecommerce.warehouse;

import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Raised when a product is missed in the stock (sold out) and requested to be fetched.
 * <p>
 * Some other service could take care of it (eg. notify a supplier).
 * <br>In the current workflow the delivery is dispatched even when something is missing. This will be just delivered later.
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"productCode", "amount"})
@ToString
public final class GoodsMissed {

    public final @NonNull Instant when;
    public final @NonNull Object productCode;
    public final @NonNull Integer amount;
}
