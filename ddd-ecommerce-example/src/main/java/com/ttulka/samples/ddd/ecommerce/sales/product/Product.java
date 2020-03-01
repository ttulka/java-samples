package com.ttulka.samples.ddd.ecommerce.sales.product;

public interface Product {

    ProductId id();

    Code code();

    Title title();

    Description description();

    Price price();

    void changePrice(Price price);
}
