package com.ttulka.samples.ddd.ecommerce.sales.product;

public interface Product {

    ProductId id();

    Code code();

    Title title();

    Description description();

    Price price();

    void changeTitle(Title title);

    void changeDescription(Description description);

    void changePrice(Price price);

    void putForSale();
}
