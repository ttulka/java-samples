package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnknownProductTest {

    @Test
    void unknown_product_has_values() {
        Product unknownProduct = new UnknownProduct();
        assertAll(
                () -> assertThat(unknownProduct.id()).isEqualTo(new ProductId(0)),
                () -> assertThat(unknownProduct.code()).isNotNull(),
                () -> assertThat(unknownProduct.title()).isNotNull(),
                () -> assertThat(unknownProduct.description()).isNotNull(),
                () -> assertThat(unknownProduct.price()).isNotNull()
        );
    }

    @Test
    void unknown_product_has_a_zero_price() {
        Product unknownProduct = new UnknownProduct();
        assertThat(unknownProduct.price()).isEqualTo(new Price(0.f));
    }

    @Test
    void change_title_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThrows(UnsupportedOperationException.class, () -> unknownProduct.changeTitle(new Title("test")));
    }

    @Test
    void change_description_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThrows(UnsupportedOperationException.class, () -> unknownProduct.changeDescription(new Description("test")));
    }

    @Test
    void change_price_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThrows(UnsupportedOperationException.class, () -> unknownProduct.changePrice(new Price(1.f)));
    }

    @Test
    void put_for_sale_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThrows(UnsupportedOperationException.class, () -> unknownProduct.putForSale());
    }
}
