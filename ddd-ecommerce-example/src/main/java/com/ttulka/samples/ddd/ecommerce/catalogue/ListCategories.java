package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.category.Categories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListCategories {

    private final Categories categories;

    public List<CategoryData> categories() {
        return categories.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    private CategoryData toData(Category category) {
        return new CategoryData(
                category.uri().value(),
                category.title().value()
        );
    }

    @RequiredArgsConstructor
    static class CategoryData {

        public final String uri;
        public final String title;
    }
}
