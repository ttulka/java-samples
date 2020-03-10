package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.ToString;

@ToString
public final class UnknownProduct implements Product {

    @Override
    public ProductId id() {
        return new ProductId(0);
    }

    @Override
    public Code code() {
        return new Code("000");
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
        // do nothing
    }

    @Override
    public void changeDescription(Description description) {
        // do nothing
    }

    @Override
    public void changePrice(Price price) {
        // do nothing
    }

    @Override
    public void putForSale() {
        // do nothing
    }
}
