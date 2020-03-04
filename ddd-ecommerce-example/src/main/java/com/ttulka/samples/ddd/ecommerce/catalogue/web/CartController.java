package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import com.ttulka.samples.ddd.ecommerce.catalogue.ListCategories;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Amount;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
class CartController {

    private final Cart cart = new Cart(); // TODO
    private final ListCategories listCategories;
    private final FindProducts findProducts;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("items", cart.items());

        decorateLayout(model);
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@NonNull String productCode, @NonNull Integer amount, Model model) {
        Product product = findProducts.byCode(new Code(productCode));
        cart.add(new Item(product.code().value(), product.title().value(), new Amount(amount)));

        return index(model);
    }

    private void decorateLayout(Model model) {
        model.addAttribute("categories", listCategories.categories());
    }
}
