package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttulka.samples.ddd.ecommerce.catalogue.AddIntoCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;
import com.ttulka.samples.ddd.ecommerce.catalogue.ListCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;

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
    private final FindProducts findProducts;

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("items",
                           new ListCart(new CartCookies(request, response)).items().stream()
                                   .map(this::toData)
                                   .toArray());
        decorateLayout(model);
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@NonNull String productCode, @NonNull Integer amount, Model model,
                      HttpServletRequest request, HttpServletResponse response) {
        new AddIntoCart(new CartCookies(request, response), findProducts)
                .product(productCode, amount);
        return index(model, request, response);
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
