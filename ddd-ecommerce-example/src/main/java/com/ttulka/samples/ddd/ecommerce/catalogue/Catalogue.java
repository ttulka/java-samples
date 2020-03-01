package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.category.Categories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Catalogue {

    private final Categories categories;
    private final FindProducts findProducts;
    private final Warehouse warehouse;

    public List<CategoryData> categories() {
        return categories.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    public List<ProductData> allProducts() {
        return findProducts.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    public List<ProductData> productsInCategory(String categoryId) {
        return findProducts.fromCategory(categoryId).stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    private ProductData toData(Product product) {
        String productId = product.id().value();
        return new ProductData(
                productId,
                product.title().value(),
                product.description().value(),
                product.price().value(),
                warehouse.leftInStock(productId)
        );
    }

    private CategoryData toData(Category category) {
        return new CategoryData(
                category.id().value(),
                category.title().value()
        );
    }

    @RequiredArgsConstructor
    class ProductData {

        public final String id;
        public final String title;
        public final String description;
        public final float price;
        public final int inStock;
    }

    @RequiredArgsConstructor
    class CategoryData {

        public final String id;
        public final String title;
    }
}
