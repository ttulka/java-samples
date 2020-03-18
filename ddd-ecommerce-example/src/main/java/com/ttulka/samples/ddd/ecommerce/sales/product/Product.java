package com.ttulka.samples.ddd.ecommerce.sales.product;

import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;

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

    void categorize(CategoryId categoryId);
}
