package com.ttulka.samples.ddd.ecommerce.catalogue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class CatalogueController {

    private final Catalogue catalogue;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", catalogue.allProducts());

        decorateLayout(model);
        return "index";
    }

    @GetMapping("/category/{categoryId}")
    public String category(@PathVariable String categoryId, Model model) {
        model.addAttribute("products", catalogue.productsInCategory(categoryId));

        decorateLayout(model);
        return "index";
    }

    private void decorateLayout(Model model) {
        model.addAttribute("categories", catalogue.categories());
    }
}
