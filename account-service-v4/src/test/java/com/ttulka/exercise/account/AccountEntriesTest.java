package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountEntriesTest {

    @Test
    void account_is_saved_with_a_new_id() {
        AccountEntries entries = new AccountEntriesInMemory();
        Long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        assertThat(id).isNotNull();
    }

    @Test
    void saved_account_is_to_find_by_id() {
        AccountEntries entries = new AccountEntriesInMemory();
        Long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));

        Optional<AccountEntries.AccountEntry> foundAccountEntry = entries.byId(id);
        assertThat(foundAccountEntry).isNotNull();
        assertThat(foundAccountEntry.isPresent()).isTrue();
    }

    @Test
    void saved_account_is_to_find_by_username() {
        AccountEntries entries = new AccountEntriesInMemory();
        String username = UUID.randomUUID().toString();
        entries.save(new AccountEntries.AccountEntry(
                null, username, "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));
        Optional<AccountEntries.AccountEntry> foundAccountEntry = entries.byUsername(username);

        assertThat(foundAccountEntry).isNotNull();
        assertThat(foundAccountEntry.isPresent()).isTrue();
    }

    @Test
    void non_saved_account_is_not_to_find_by_id() {
        AccountEntries entries = new AccountEntriesInMemory();
        Optional<AccountEntries.AccountEntry> foundAccountEntry = entries.byId(Long.MAX_VALUE);

        assertThat(foundAccountEntry).isNotNull();
        assertThat(foundAccountEntry.isPresent()).isFalse();
    }

    @Test
    void non_saved_account_is_not_to_find_by_username() {
        AccountEntries entries = new AccountEntriesInMemory();
        Optional<AccountEntries.AccountEntry> foundAccountEntry = entries.byUsername(UUID.randomUUID().toString());

        assertThat(foundAccountEntry).isNotNull();
        assertThat(foundAccountEntry.isPresent()).isFalse();
    }

    @Test
    void deleted_account_is_not_to_find() {
        AccountEntries entries = new AccountEntriesInMemory();
        Long id = entries.save(new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now()));
        entries.delete(id);

        Optional<AccountEntries.AccountEntry> foundAccountEntry = entries.byId(id);
        assertThat(foundAccountEntry).isNotNull();
        assertThat(foundAccountEntry.isPresent()).isFalse();
    }

    @Test
    void attributes_are_saved() {
        AccountEntries entries = new AccountEntriesInMemory();
        String username = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        Long id = entries.save(new AccountEntries.AccountEntry(
                null, username, "test@example.com", "secret".getBytes(), "salt", now));

        AccountEntries.AccountEntry savedEntry = entries.byId(id).get();
        assertThat(savedEntry.id).isEqualTo(id);
        assertThat(savedEntry.username).isEqualTo(username);
        assertThat(savedEntry.email).isEqualTo("test@example.com");
        assertThat(savedEntry.encryptedPassword).isEqualTo("secret".getBytes());
        assertThat(savedEntry.salt).isEqualTo("salt");
        assertThat(savedEntry.lastLoggedIn).isEqualTo(now);
    }

    @Test
    void attributes_are_updated() {
        AccountEntries entries = new AccountEntriesInMemory();
        String username = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        Long id = entries.save(new AccountEntries.AccountEntry(
                null, username, "test@example.com", "secret".getBytes(), "salt", now));

        Long updatedId = entries.save(new AccountEntries.AccountEntry(
                id, username, "updated@example.com", "updated".getBytes(), "updated", now.plusMinutes(1)));

        assertThat(updatedId).isEqualTo(id);

        AccountEntries.AccountEntry savedEntry = entries.byId(id).get();
        assertThat(savedEntry.id).isEqualTo(id);
        assertThat(savedEntry.username).isEqualTo(username);
        assertThat(savedEntry.email).isEqualTo("updated@example.com");
        assertThat(savedEntry.encryptedPassword).isEqualTo("updated".getBytes());
        assertThat(savedEntry.salt).isEqualTo("updated");
        assertThat(savedEntry.lastLoggedIn).isEqualTo(now.plusMinutes(1));
    }

    @Test
    void attributes_are_not_persisted_without_calling_save() {
        AccountEntries entries = new AccountEntriesInMemory();
        AccountEntries.AccountEntry entry = new AccountEntries.AccountEntry(
                null, UUID.randomUUID().toString(), "test@example.com", "secret".getBytes(), "salt", ZonedDateTime.now());
        Long id = entries.save(entry);

        byte[] encryptedPassword = entry.encryptedPassword.clone();
        entry.encryptedPassword[0] += 1;

        Optional<AccountEntries.AccountEntry> entry2 = entries.byId(id);
        assertThat(entry2.get().encryptedPassword).isEqualTo(encryptedPassword);
    }
}
