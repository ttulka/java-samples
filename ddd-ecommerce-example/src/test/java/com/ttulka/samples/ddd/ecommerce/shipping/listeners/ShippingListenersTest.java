package com.ttulka.samples.ddd.ecommerce.shipping.listeners;

import java.time.Instant;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.UpdateDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ShippingListenersTest {

    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @MockBean
    private PrepareDelivery prepareDelivery;
    @MockBean
    private UpdateDelivery updateDelivery;
    @MockBean
    private FindDeliveries findDeliveries;

    @Test
    void on_order_placed_a_delivery_is_prepared() {
        runTx(() -> eventPublisher.raise(
                new OrderPlaced(Instant.now(), 123L,
                                List.of(new OrderPlaced.OrderItemData("test-code", "Title", 123.5f, 2)),
                                new OrderPlaced.CustomerData("test name", "test address"))));

        verify(prepareDelivery).prepare(new OrderId(123L),
                                        List.of(new DeliveryItem(new ProductCode("test-code"), new Quantity(2))),
                                        new Address(new Person("test name"), new Place("test address")));
    }

    @Test
    void on_goods_fetched_a_delivery_is_updated() {
        runTx(() -> eventPublisher.raise(new GoodsFetched(Instant.now(), 123L)));

        verify(updateDelivery).asFetched(new OrderId(123L));
    }

    @Test
    void on_payment_received_a_delivery_is_updated() {
        runTx(() -> eventPublisher.raise(new PaymentReceived(Instant.now(), 123L)));

        verify(updateDelivery).asPaid(new OrderId(123L));
    }

    private void runTx(Runnable runnable) {
        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                runnable.run();
            }
        });
    }
}
