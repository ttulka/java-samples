package com.ttulka.samples.ddd.ecommerce.catalogue.web;

import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@ControllerAdvice(basePackageClasses = WebLayoutAdvice.class)
@RequiredArgsConstructor
class WebLayoutAdvice {

    private final @NonNull Catalogue catalogue;

    @ModelAttribute
    public void decorateWithCategories(Model model) {
        model.addAttribute("categories", catalogue.categories());
    }
}
