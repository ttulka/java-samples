package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.ListCategories;

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

    private final Cart cart;
    private final ListCategories listCategories;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("items", cart.items());

        decorateLayout(model);
        return "cart";
    }

    @PostMapping
    public String add(@NonNull String productId, @NonNull Integer amount, Model model) {
        cart.add(productId, amount);

        decorateLayout(model);
        return "index";
    }

    private void decorateLayout(Model model) {
        model.addAttribute("categories", listCategories.categories());
    }
}
