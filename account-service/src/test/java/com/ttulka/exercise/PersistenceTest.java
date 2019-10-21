package com.ttulka.exercise;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import com.ttulka.exercise.impl.InMemoryAccountRepository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceTest {

    @Test
    void valid_entity_is_saved() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");

        repo.save(account);
        assertThat(account.getId()).isNotNull();
    }

    @Test
    void saved_entity_is_to_find_by_id() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Account foundAccount = repo.findById(account.getId());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(account.getId());
    }

    @Test
    void saved_entity_is_to_find_by_username() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Account foundAccount = repo.findByName(username);
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(account.getId());
    }

    @Test
    void non_existing_entity_is_not_to_find_by_id() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        Account foundAccount = repo.findById(Long.MAX_VALUE);
        assertThat(foundAccount).isNull();
    }

    @Test
    void non_existing_entity_is_not_to_find_by_username() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        Account foundAccount = repo.findByName(UUID.randomUUID().toString());
        assertThat(foundAccount).isNull();
    }

    @Test
    void deleted_entity_is_not_to_find() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        repo.delete(account);

        Account foundAccount = repo.findById(account.getId());
        assertThat(foundAccount).isNull();
    }

    @Test
    void already_saved_entity_is_updated_when_saved_again() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        Long savedId = account.getId();
        repo.save(account);

        assertThat(account.getId()).isEqualTo(savedId);
    }

    @Test
    void lastLogin_is_updated_after_entity_was_updated() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        String username = UUID.randomUUID().toString();
        Account account = new Account(username, "test@example.com", "pwd1");
        repo.save(account);

        ZonedDateTime editedLastLogin = ZonedDateTime.now();
        account.setLastLogin(editedLastLogin);
        repo.save(account);

        Account updatedAccount = repo.findById(account.getId());
        assertThat(updatedAccount.getLastLogin()).isEqualTo(editedLastLogin);
    }

    @Test
    void attributes_are_not_persisted_without_calling_save() {
        PersistenceInterface repo = new InMemoryAccountRepository();

        Account account = new Account(UUID.randomUUID().toString(), "test@example.com", "pwd1");
        repo.save(account);

        byte[] encryptedPassword = account.getEncryptedPassword().clone();

        account.getEncryptedPassword()[0] += 1;

        Account account2 = repo.findById(account.getId());
        assertThat(account2.getEncryptedPassword()).isEqualTo(encryptedPassword);
    }
}
