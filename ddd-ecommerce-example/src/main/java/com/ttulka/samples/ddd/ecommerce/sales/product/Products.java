package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.List;

public interface Products {

    List<Product> all();

    List<Product> inCategory(String categoryId);
}
