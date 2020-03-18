package com.ttulka.samples.ddd.ecommerce.shipping.rest;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindDeliveries findDeliveries;
    @MockBean
    private Catalogue catalogue;

    @Test
    void all_deliveries() throws Exception {
        when(findDeliveries.all()).thenReturn(List.of(
                new DeliveryInfo(new DeliveryId(123L), new OrderId("TEST-1"))));

        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(123)))
                .andExpect(jsonPath("$[0].orderId", is("TEST-1")));
    }

    @Test
    void delivery_by_order() throws Exception {
        when(findDeliveries.byOrderId(eq(new OrderId("TEST-1")))).thenReturn(
                testDelivery(new DeliveryId(123L), new OrderId("TEST-1"), "test person", "test place", "test-1", 25));

        mockMvc.perform(get("/delivery/order/TEST-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.address.person", is("test person")))
                .andExpect(jsonPath("$.address.place", is("test place")))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].code", is("test-1")))
                .andExpect(jsonPath("$.items[0].quantity", is(25)))
                .andExpect(jsonPath("$.dispatched", is(false)));
    }

    private Delivery testDelivery(DeliveryId deliveryId, OrderId orderId,
                                  String person, String place, String productCode, Integer quantity) {
        return new Delivery() {
            @Override
            public DeliveryId id() {
                return deliveryId;
            }

            @Override
            public OrderId orderId() {
                return orderId;
            }

            @Override
            public List<DeliveryItem> items() {
                return List.of(new DeliveryItem(new ProductCode(productCode), new Quantity(quantity)));
            }

            @Override
            public Address address() {
                return new Address(new Person(person), new Place(place));
            }

            @Override
            public void prepare() {
            }

            @Override
            public void markAsFetched() {
            }

            @Override
            public void markAsPaid() {
            }

            @Override
            public void dispatch() {
            }

            @Override
            public boolean isDispatched() {
                return false;
            }

            @Override
            public boolean isReadyToDispatch() {
                return false;
            }
        };
    }
}
