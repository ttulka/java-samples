package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRegistryTest {

    private final AccountEntries entries = new AccountEntriesInMemory();
    private final AccountFactory factory = new AccountFactoryImpl(entries);

    @Test
    void registered_account_is_to_find_by_id() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        Optional<Account> foundAccount = registry.byId(id);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
    }

    @Test
    void registered_account_is_to_find_by_username() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        String username = UUID.randomUUID().toString();
        long id = entries.save(new AccountEntries.AccountEntry(
                null, username, "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        Optional<Account> foundAccount = registry.byUsername(username);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
    }

    @Test
    void non_registered_account_is_not_to_find_by_id() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        Optional<Account> foundAccount = registry.byId(Long.MAX_VALUE);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void non_registered_account_is_not_to_find_by_username() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        Optional<Account> foundAccount = registry.byUsername(UUID.randomUUID().toString());

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void unregistered_account_is_not_to_find() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        registry.unregister(id);
        Optional<Account> foundAccount = registry.byId(id);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void last_logged_in_is_updated() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        ZonedDateTime lastLoggedInUpdated = ZonedDateTime.now().plusDays(1);
        registry.updateLastLoggedIn(id, lastLoggedInUpdated);

        assertThat(entries.byId(id).get().lastLoggedIn).isEqualTo(lastLoggedInUpdated);
    }

    @Test
    void encrypted_password_is_changed() {
        AccountRegistry registry = new AccountRegistryImpl(factory, entries);
        long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        registry.changeEncryptedPassword(id, "updated".getBytes());

        assertThat(entries.byId(id).get().encryptedPassword).isEqualTo("updated".getBytes());
    }
}
