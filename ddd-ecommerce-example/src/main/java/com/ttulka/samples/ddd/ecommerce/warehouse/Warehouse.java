package com.ttulka.samples.ddd.ecommerce.warehouse;

public interface Warehouse {

    InStock leftInStock(ProductCode productCode);
}
