package com.ttulka.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class AbstractTest {

    @Test
    void successful() {
        assertTrue(true);
    }

    @DisabledOnTag("DisableMe")
    @Test
    void failing() {
        assertTrue(false);
    }
}
