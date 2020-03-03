package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.UUID;

import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

public final class UnknownProduct implements Product {

    @Override
    public ProductId id() {
        return new ProductId(-1L);
    }

    @Override
    public Code code() {
        return new Code("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public Title title() {
        return new Title("unknown product");
    }

    @Override
    public Description description() {
        return new Description("This product is not to be found in the catalogue.");
    }

    @Override
    public Price price() {
        return new Price(0.0F);
    }

    @Override
    public void changeTitle(Title title) {
        throw new UnsupportedOperationException("Cannot change the title: unknown product.");
    }

    @Override
    public void changeDescription(Description description) {
        throw new UnsupportedOperationException("Cannot change the description: unknown product.");
    }

    @Override
    public void changePrice(Price price) {
        throw new UnsupportedOperationException("Cannot change the price: unknown product.");
    }

    @Override
    public void putForSale() {
        throw new UnsupportedOperationException("Cannot put for sale: unknown product.");
    }
}
