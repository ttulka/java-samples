package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.UUID;

import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

final class UnknownProduct implements Product {

    @Override
    public ProductId id() {
        return new ProductId(-1L);
    }

    @Override
    public Code code() {
        return new Code(UUID.randomUUID().toString());
    }

    @Override
    public Title title() {
        return new Title("unknown");
    }

    @Override
    public Description description() {
        return new Description("This product is not to be found in the catalogue.");
    }

    @Override
    public Price price() {
        return new Price(0.0F);
    }

    @Override
    public void changePrice(Price price) {
        throw new UnsupportedOperationException();
    }
}
