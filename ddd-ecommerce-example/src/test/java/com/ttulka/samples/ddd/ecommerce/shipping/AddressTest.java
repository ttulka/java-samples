package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AddressTest {

    @Test
    void address_values() {
        Person person = new Person("test");
        Place place = new Place("test");
        Address address = new Address(person, place);

        assertAll(
                () -> assertThat(address.person()).isEqualTo(person),
                () -> assertThat(address.place()).isEqualTo(place)
        );
    }
}
