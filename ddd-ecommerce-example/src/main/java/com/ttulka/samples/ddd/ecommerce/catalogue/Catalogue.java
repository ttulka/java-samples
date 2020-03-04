package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Catalogue {

    private final FindCategories findCategories;
    private final FindProducts findProducts;
    private final Warehouse warehouse;

    public List<CatalogueProductData> allProducts() {
        return findProducts.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    public List<CatalogueProductData> productsInCategory(String categoryUri) {
        return findProducts.fromCategory(new Uri(categoryUri)).stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    public List<CatalogueCategoryData> categories() {
        return findCategories.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    private CatalogueProductData toData(Product product) {
        return new CatalogueProductData(
                product.code().value(),
                product.title().value(),
                product.description().value(),
                product.price().value(),
                warehouse.leftInStock(product.id()).amount());
    }

    private CatalogueCategoryData toData(Category category) {
        return new CatalogueCategoryData(
                category.uri().value(),
                category.title().value());
    }

    @RequiredArgsConstructor
    public static class CatalogueProductData {

        public final String code;
        public final String title;
        public final String description;
        public final float price;
        public final int inStock;
    }

    @RequiredArgsConstructor
    public static class CatalogueCategoryData {

        public final String uri;
        public final String title;
    }
}
