package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.List;

public interface FindProducts {

    List<Product> all();

    List<Product> fromCategory(String categoryId);
}
