package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountFactoryTest {

    @Test
    void account_is_created_simplified() {
        AccountFactory factory = new AccountFactoryImpl(new AccountEntriesInMemory());
        Account account = factory.newAccount(
                UUID.randomUUID().toString(), "test@example.com", "pwd1");

        assertThat(account).isNotNull();
    }

    @Test
    void account_is_created() {
        AccountFactory factory = new AccountFactoryImpl(new AccountEntriesInMemory());
        Account account = factory.newAccount(
                Long.MAX_VALUE, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());

        assertThat(account).isNotNull();
    }
}
