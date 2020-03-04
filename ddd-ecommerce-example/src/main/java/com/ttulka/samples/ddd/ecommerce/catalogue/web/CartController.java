package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import java.util.Map;

import com.ttulka.samples.ddd.ecommerce.catalogue.AddIntoCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;
import com.ttulka.samples.ddd.ecommerce.catalogue.ListCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;

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

    private final Catalogue catalogue;

    private final AddIntoCart addIntoCart;
    private final ListCart listCart;

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
        addIntoCart.product(productCode, amount);
        return index(model);
    }

    private Map<String, Object> toData(Item item) {
        return Map.of("code", item.productCode(),
                      "title", item.title(),
                      "amount", item.amount().value());
    }

    private void decorateLayout(Model model) {
        model.addAttribute("categories", catalogue.categories());
    }
}
