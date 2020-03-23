package com.ttulka.samples.ddd.ecommerce.sales;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;

public interface FindCategories {

    /**
     * Lists all categories.
     *
     * @return all categories
     */
    List<Category> all();

    /**
     * Finds a category by the category ID.
     *
     * @param id the category ID
     * @return the category
     */
    Category byId(CategoryId id);
}
