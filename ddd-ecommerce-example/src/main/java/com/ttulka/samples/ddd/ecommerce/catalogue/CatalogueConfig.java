package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.AddIntoCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.ListCart;
import com.ttulka.samples.ddd.ecommerce.sales.category.FindCategories;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    Catalogue catalogue(FindProducts findProducts, Warehouse warehouse) {
        return new Catalogue(findProducts, warehouse);
    }

    @Bean
    ListCategories listCategories(FindCategories findCategories) {
        return new ListCategories(findCategories);
    }

    @Bean
    Cart cart() {
        return new Cart();
    }

    @Bean
    ListCart listCart(Cart cart) {
        return new ListCart(cart);
    }

    @Bean
    AddIntoCart addIntoCart(Cart cart, FindProducts findProducts) {
        return new AddIntoCart(cart, findProducts);
    }
}
