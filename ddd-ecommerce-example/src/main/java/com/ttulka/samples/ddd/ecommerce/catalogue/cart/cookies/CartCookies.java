package com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Amount;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(of = "cookie")
public final class CartCookies implements Cart {

    private final HttpServletResponse response;

    private String cookie;

    public CartCookies(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        Cookie[] cookies = request.getCookies();
        this.cookie = cookies != null
                      ? Arrays.stream(cookies)
                              .filter(c -> "cart".equals(c.getName()))
                              .map(Cookie::getValue)
                              .findAny()
                              .orElse("")
                      : "";
    }

    @Override
    public List<Item> items() {
        return Collections.unmodifiableList(parsedItems(cookie));
    }

    @Override
    public void add(@NonNull Item toAdd) {
        List<Item> currentItems = parsedItems(cookie);

        Amount alreadyInCart = currentItems.stream()
                .filter(toAdd::equals)
                .map(Item::amount)
                .findAny().orElse(new Amount(0));

        List<Item> items = new ArrayList<>(
                currentItems.stream()
                        .filter(Predicate.not(toAdd::equals))
                        .collect(Collectors.toList()));

        items.add(toAdd.add(alreadyInCart));

        // TODO ItemCookie will implement this later:
        response.addCookie(cartCookie(cookie = items.stream()
                .map(item -> String.format("%s|%s|%d",
                                           item.productCode(),
                                           item.title().replace(" ", "_"),
                                           item.amount().value()))
                .collect(Collectors.joining("#"))));
    }

    @Override
    public void remove(String productCode) {
        // TODO ItemCookie will implement this later:
        response.addCookie(cartCookie(cookie = parsedItems(cookie).stream()
                .filter(item -> !item.productCode().equals(productCode))
                .map(item -> String.format("%s|%s|%d",
                                           item.productCode(),
                                           item.title().replace(" ", "_"),
                                           item.amount().value()))
                .collect(Collectors.joining("#"))));
    }

    @Override
    public void empty() {
        response.addCookie(cartCookie(cookie = ""));
    }

    // TODO ItemCookie will implement this later:
    private List<Item> parsedItems(String cookie) {
        return Arrays.stream(cookie.split("#"))
                .filter(Predicate.not(String::isBlank))
                .map(this::parseItem)
                .collect(Collectors.toList());
    }

    private Item parseItem(String cookie) {
        String[] item = cookie.split("\\|");
        return new Item(item[0], item[1].replace("_", " "), new Amount(Integer.parseInt(item[2])));
    }

    private Cookie cartCookie(String value) {
        Cookie cookie = new Cookie("cart", value);
        cookie.setPath("/");
        return cookie;
    }
}
