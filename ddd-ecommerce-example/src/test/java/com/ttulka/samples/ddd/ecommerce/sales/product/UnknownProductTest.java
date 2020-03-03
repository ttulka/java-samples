package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnknownProductTest {

    @Test
    void unknown_product_has_values() {
        Product unknownProduct = new UnknownProduct();

        assertThat(unknownProduct.id()).isEqualTo(new ProductId(-1L));
        assertThat(unknownProduct.code()).isNotNull();
        assertThat(unknownProduct.title()).isNotNull();
        assertThat(unknownProduct.description()).isNotNull();
        assertThat(unknownProduct.price()).isNotNull();
    }

    @Test
    void unknown_product_has_a_zero_price() {
        Product unknownProduct = new UnknownProduct();
        assertThat(unknownProduct.price()).isEqualTo(new Price(0.f));
    }

    @Test
    void change_title_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThatThrownBy(() -> unknownProduct.changeTitle(new Title("test")));
    }

    @Test
    void change_description_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThatThrownBy(() -> unknownProduct.changeDescription(new Description("test")));
    }

    @Test
    void change_price_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThatThrownBy(() -> unknownProduct.changePrice(new Price(1.f)));
    }

    @Test
    void put_for_sale_fails() {
        Product unknownProduct = new UnknownProduct();
        assertThatThrownBy(() -> unknownProduct.putForSale());
    }
}
