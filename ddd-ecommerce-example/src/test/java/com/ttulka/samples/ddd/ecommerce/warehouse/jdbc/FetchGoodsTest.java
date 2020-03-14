package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.Amount;
import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;
import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.ToFetch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = WarehouseJdbcConfig.class)
class FetchGoodsTest {

    @Autowired
    private FetchGoods fetchGoods;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void fetched_goods_raises_an_event() {
        fetchGoods.fromOrder(new OrderId(123L), List.of(
                new ToFetch(new ProductCode("test"), new Amount(123))));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(GoodsFetched.class);
                    GoodsFetched goodsFetched = (GoodsFetched) event;
                    assertAll(
                            () -> assertThat(goodsFetched.when).isNotNull(),
                            () -> assertThat(goodsFetched.orderId).isEqualTo(123L)
                    );
                    return true;
                }));
    }
}
