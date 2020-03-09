package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class CatalogueController {

    private final @NonNull Catalogue catalogue;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", catalogue.allProducts());
        return "catalogue";
    }

    @GetMapping("/category/{categoryId}")
    public String category(@PathVariable @NonNull String categoryId, Model model) {
        model.addAttribute("products", catalogue.productsInCategory(categoryId));
        return "catalogue";
    }
}
