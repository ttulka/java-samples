package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttulka.samples.ddd.ecommerce.catalogue.AddIntoCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.ListCart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;
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

    private final @NonNull FindProducts findProducts;

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        cartIntoModel(new CartCookies(request, response), model);
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@NonNull String productCode, @NonNull Integer quantity,
                      HttpServletRequest request, HttpServletResponse response) {
        Cart cart = new CartCookies(request, response);
        new AddIntoCart(cart, findProducts).product(productCode, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String remove(@NonNull String productCode,
                         HttpServletRequest request, HttpServletResponse response) {
        Cart cart = new CartCookies(request, response);
        cart.remove(productCode);

        return "redirect:/cart";
    }

    private Map<String, Object> toData(CartItem item) {
        return Map.of("code", item.productCode(),
                      "title", item.title(),
                      "quantity", item.quantity().value());
    }

    private void cartIntoModel(Cart cart, Model model) {
        model.addAttribute("items",
                           new ListCart(cart).items().stream()
                                   .map(this::toData)
                                   .toArray());
    }
}
