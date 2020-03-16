package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.category.Category;
import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class Catalogue {

    private final @NonNull FindCategories findCategories;
    private final @NonNull FindProducts findProducts;
    private final @NonNull Warehouse warehouse;

    public List<CatalogueProductData> allProducts() {
        return findProducts.all().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    public List<CatalogueProductData> productsInCategory(@NonNull String categoryUri) {
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
                warehouse.leftInStock(new ProductCode(product.code().value())).amount());
    }

    private CatalogueCategoryData toData(Category category) {
        return new CatalogueCategoryData(
                category.uri().value(),
                category.title().value());
    }

    @Value
    public static class CatalogueProductData {

        public final String code;
        public final String title;
        public final String description;
        public final float price;
        public final int inStock;
    }

    @Value
    public static class CatalogueCategoryData {

        public final String uri;
        public final String title;
    }
}
