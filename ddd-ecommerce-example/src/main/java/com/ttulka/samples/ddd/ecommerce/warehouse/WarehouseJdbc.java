package com.ttulka.samples.ddd.ecommerce.warehouse;

final class WarehouseJdbc implements Warehouse {

    @Override
    public int leftInStock(String productId) {
        return 0;
    }
}
