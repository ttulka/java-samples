package com.ttulka.samples.ddd.ecommerce.sales;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;

public interface FindCategories {

    List<Category> all();

    Category byId(CategoryId id);
}
