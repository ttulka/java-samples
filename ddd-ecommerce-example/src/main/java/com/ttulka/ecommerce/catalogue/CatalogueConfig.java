package com.ttulka.ecommerce.catalogue;

import com.ttulka.ecommerce.sales.FindCategories;
import com.ttulka.ecommerce.sales.FindProducts;
import com.ttulka.ecommerce.sales.PlaceOrder;
import com.ttulka.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    Catalogue catalogue(FindCategories findCategories, FindProducts findProducts, Warehouse warehouse) {
        return new Catalogue(findCategories, findProducts, warehouse);
    }

    @Bean
    CartItems cartItems(FindProducts findProducts) {
        return new CartItems(findProducts);
    }

    @Bean
    PlaceOrderFromCart placeOrderFromCart(PlaceOrder placeOrder, FindProducts findProducts) {
        return new PlaceOrderFromCart(placeOrder, findProducts);
    }
}
