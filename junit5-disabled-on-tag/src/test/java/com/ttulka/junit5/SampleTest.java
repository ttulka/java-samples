package com.ttulka.junit5;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Sample")
class SampleTest {

    @Test
    void test1() {
        assertTrue(true);
    }

    @DisabledOnTag("Sample")
    @Test
    void test2() {
        assertTrue(false);
    }
}
