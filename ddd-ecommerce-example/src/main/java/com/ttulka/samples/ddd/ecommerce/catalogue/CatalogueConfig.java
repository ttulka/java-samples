package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.sales.category.Categories;
import com.ttulka.samples.ddd.ecommerce.sales.product.Products;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    Catalogue catalogue(Categories categories, Products products, Warehouse warehouse) {
        return new Catalogue(categories, products, warehouse);
    }
}