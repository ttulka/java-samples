package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.sales.category.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    Catalogue catalogue(FindCategories findCategories, FindProducts findProducts, Warehouse warehouse) {
        return new Catalogue(findProducts, warehouse);
    }

    @Bean
    Cart cart(FindProducts findProducts) {
        return new Cart(findProducts);
    }

    @Bean
    ListCategories listCategories(FindCategories findCategories) {
        return new ListCategories(findCategories);
    }
}
