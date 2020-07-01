package com.ttulka.junit5;

import org.junit.jupiter.api.Tag;

@Tag("DifferentTagShouldBeIgnored")
class UntaggedTest extends AbstractTest {
}
