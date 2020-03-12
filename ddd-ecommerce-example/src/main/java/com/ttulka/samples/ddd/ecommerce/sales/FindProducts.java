package com.ttulka.samples.ddd.ecommerce.sales;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;

public interface FindProducts {

    List<Product> all();

    List<Product> fromCategory(Uri categoryUri);

    Product byCode(Code code);
}
