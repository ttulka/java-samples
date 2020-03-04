package com.ttulka.samples.ddd.ecommerce.warehouse;

import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;

public interface Warehouse {

    InStock leftInStock(ProductId productId);
}
