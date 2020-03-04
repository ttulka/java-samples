package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import java.util.Map;

import com.ttulka.samples.ddd.ecommerce.catalogue.ListCategories;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.AddIntoCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.ListCart;

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

    private final AddIntoCart addIntoCart;
    private final ListCart listCart;
    private final ListCategories listCategories;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("items", listCart.items().stream()
                .map(this::toData)
                .toArray()
        );
        decorateLayout(model);
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@NonNull String productCode, @NonNull Integer amount, Model model) {
        addIntoCart.item(productCode, amount);
        return index(model);
    }

    private Map<String, Object> toData(Item item) {
        return Map.of("code", item.productCode(),
                      "title", item.title(),
                      "amount", item.amount().value());
    }

    private void decorateLayout(Model model) {
        model.addAttribute("categories", listCategories.categories());
    }
}
