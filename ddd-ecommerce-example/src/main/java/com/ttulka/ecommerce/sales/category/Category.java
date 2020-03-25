package com.ttulka.ecommerce.sales.category;

public interface Category {

    CategoryId id();

    Uri uri();

    Title title();

    void changeTitle(Title title);
}
