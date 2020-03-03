package com.ttulka.samples.ddd.ecommerce.sales.category;

import java.util.List;

public interface FindCategories {

    List<Category> all();

    Category byId(CategoryId id);
}
