package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;
import com.ttulka.samples.ddd.ecommerce.catalogue.order.Address;
import com.ttulka.samples.ddd.ecommerce.catalogue.order.Customer;
import com.ttulka.samples.ddd.ecommerce.catalogue.order.Name;
import com.ttulka.samples.ddd.ecommerce.catalogue.order.Order;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
class OrderController {

    @GetMapping
    public String index() {
        return "order";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String newOrder(String name, String address,
                           HttpServletRequest request, HttpServletResponse response) {
        Cart cart = new CartCookies(request, response);
        new Order(/* TODO */,
                new Customer(new Name(name), new Address(address)))
                .sumbit();

        return "redirect:/order/success";
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request, HttpServletResponse response) {
        new CartCookies(request, response).empty();

        return "order-success";
    }
}
