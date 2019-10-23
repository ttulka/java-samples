package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest {

    @Test
    void valid_entity_is_saved() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");

        repo.save(account);
        assertThat(account.getId()).isNotNull();
    }

    @Test
    void saved_entity_is_to_find_by_id() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Optional<Account> foundAccount = repo.byId(account.getId());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
        assertThat(foundAccount.get().getId()).isEqualTo(account.getId());
        assertThat(foundAccount.get().getUsername()).isEqualTo(username);
        assertThat(foundAccount.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void saved_entity_is_to_find_by_username() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Optional<Account> foundAccount = repo.byUsername(username);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isTrue();
        assertThat(foundAccount.get().getId()).isEqualTo(account.getId());
        assertThat(foundAccount.get().getUsername()).isEqualTo(username);
        assertThat(foundAccount.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void non_existing_entity_is_not_to_find_by_id() {
        AccountRepository repo = new AccountRepositoryInMemory();

        Optional<Account> foundAccount = repo.byId(Long.MAX_VALUE);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void non_existing_entity_is_not_to_find_by_username() {
        AccountRepository repo = new AccountRepositoryInMemory();

        Optional<Account> foundAccount = repo.byUsername(UUID.randomUUID().toString());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void deleted_entity_is_not_to_find() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        repo.delete(account);

        Optional<Account> foundAccount = repo.byId(account.getId());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.isPresent()).isFalse();
    }

    @Test
    void already_saved_entity_is_updated_when_saved_again() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Long savedId = account.getId();
        repo.save(account);

        assertThat(account.getId()).isEqualTo(savedId);
    }

    @Test
    void lastLogin_is_updated_after_entity_was_updated() {
        AccountRepository repo = new AccountRepositoryInMemory();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        ZonedDateTime editedLastLogin = ZonedDateTime.now();
        account.setLastLoggedIn(editedLastLogin);
        repo.save(account);

        Optional<Account> updatedAccount = repo.byId(account.getId());
        assertThat(updatedAccount.get().getLastLoggedIn()).isEqualTo(editedLastLogin);
    }

    @Test
    void attributes_are_not_persisted_without_calling_save() {
        AccountRepository repo = new AccountRepositoryInMemory();

        Account account = new Account(UUID.randomUUID().toString(), "test@example.com", "pwd1");
        repo.save(account);

        byte[] encryptedPassword = account.getEncryptedPassword().clone();

        account.getEncryptedPassword()[0] += 1;

        Optional<Account> account2 = repo.byId(account.getId());
        assertThat(account2.get().getEncryptedPassword()).isEqualTo(encryptedPassword);
    }
}
