package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.sales.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.PlaceOrder;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    Catalogue catalogue(FindCategories findCategories, FindProducts findProducts, Warehouse warehouse) {
        return new Catalogue(findCategories, findProducts, warehouse);
    }

    @Bean
    PlaceOrderFromCart placeOrderFromCart(PlaceOrder placeOrder, FindProducts findProducts) {
        return new PlaceOrderFromCart(placeOrder, findProducts);
    }
}
