package com.ttulka.samples.ddd.ecommerce.catalogue;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CatalogueController {

    @GetMapping
    public String index() {
        return "index";
    }
}
