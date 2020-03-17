package com.ttulka.samples.ddd.ecommerce.warehouse.rest;

import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
class WarehouseController {

    private final @NonNull Warehouse warehouse;

    @GetMapping("/stock/{productCode}")
    public Integer productsInStock(@PathVariable @NonNull String productCode) {
        return warehouse.leftInStock(new ProductCode(productCode)).amount();
    }
}
