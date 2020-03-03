package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;

public interface FindProducts {

    List<Product> all();

    List<Product> fromCategory(Uri categoryUri);

    Product byId(ProductId id);
}
