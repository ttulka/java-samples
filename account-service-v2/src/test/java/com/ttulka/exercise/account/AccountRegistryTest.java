package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRegistryTest {

    @Test
    void account_is_registered_with_a_new_id() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Long id = registry.register(UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());

        assertThat(id).isNotNull();
    }

    @Test
    void registered_account_is_to_find_by_id() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Long id = registry.register(UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());

        Optional<Account> foundAccount = registry.byId(id);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
    }

    @Test
    void registered_account_is_to_find_by_username() {
        AccountRegistry registry = new AccountRegistryInMemory();
        String username = UUID.randomUUID().toString();
        registry.register(username, "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());
        Optional<Account> foundAccount = registry.byUsername(username);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
    }

    @Test
    void non_registered_account_is_not_to_find_by_id() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Optional<Account> foundAccount = registry.byId(Long.MAX_VALUE);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void non_registered_account_is_not_to_find_by_username() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Optional<Account> foundAccount = registry.byUsername(UUID.randomUUID().toString());

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void unregistered_account_is_not_to_find() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Long id = registry.register(UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());
        registry.unregister(id);

        Optional<Account> foundAccount = registry.byId(id);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void last_logged_in_is_updated() {
        AccountRegistry registry = new AccountRegistryInMemory();
        Long id = registry.register(UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());

        ZonedDateTime lastLoggedInUpdated = ZonedDateTime.now().plusDays(1);
        registry.updateLastLoggedIn(id, lastLoggedInUpdated);

        Optional<Account> updatedAccount = registry.byId(id);
        assertThat(updatedAccount.get().hasLoggedInSince(lastLoggedInUpdated.minusSeconds(1))).isTrue();
    }

//    @Test
//    void encrypted_password_is_changed() {
//        AccountRegistry registry = new InMemoryAccountRegistry();
//
//        Long id = registry.register(UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());
//
//        registry.changeEncryptedPassword(id, "updated".getBytes());
//
//        Optional<Account> updatedAccount = registry.byId(id);
//        assertThat(updatedAccount.get().getEncryptedPassword()).isTrue();
//    }
//
//    @Test
//    void attributes_are_not_persisted_without_calling_save() {
//        AccountRegistry registry = new InMemoryAccountRegistry();
//
//        Account account = new RegisteredAccount(UUID.randomUUID().toString(), "test@example.com", "pwd1");
//        registry.save(account);
//
//        byte[] encryptedPassword = account.getEncryptedPassword().clone();
//        account.getEncryptedPassword()[0] += 1;
//
//        Optional<Account> account2 = registry.byId(account.getId());
//        assertThat(account2.getEncryptedPassword()).isEqualTo(encryptedPassword);
//    }
}
