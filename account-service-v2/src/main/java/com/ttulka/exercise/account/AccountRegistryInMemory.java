package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

class AccountRegistryInMemory implements AccountRegistry {

    private final Map<Long, AccountEntry> accountEntries = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public long register(@NonNull String username, @NonNull String email,
                         @NonNull byte[] encryptedPassword, @NonNull String salt,
                         ZonedDateTime lastLoggedIn) {
        long id = idSequence.incrementAndGet();
        accountEntries.put(id, new AccountEntry(id, username, email, encryptedPassword, salt, lastLoggedIn));
        return id;
    }

    @Override
    public Optional<Account> byId(long id) {
        if (accountEntries.containsKey(id)) {
            AccountEntry entry = accountEntries.get(id);
            return Optional.of(new AccountImpl(
                    entry.id,
                    entry.username,
                    entry.email,
                    entry.encryptedPassword.clone(),
                    entry.salt,
                    entry.lastLoggedIn,
                    this
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> byUsername(@NonNull String username) {
        return accountEntries.values().stream()
                .filter(entry -> username.equals(entry.username))
                .findAny()
                .map(entry -> new AccountImpl(
                        entry.id,
                        entry.username,
                        entry.email,
                        entry.encryptedPassword.clone(),
                        entry.salt,
                        entry.lastLoggedIn,
                        this
                ));
    }

    @Override
    public void updateLastLoggedIn(long id, @NonNull ZonedDateTime lastLoggedIn) {
        if (accountEntries.containsKey(id)) {
            accountEntries.get(id).lastLoggedIn = lastLoggedIn;
        }
    }

    @Override
    public void changeEncryptedPassword(long id, byte[] encryptedPassword) {
        if (accountEntries.containsKey(id)) {
            accountEntries.get(id).encryptedPassword = encryptedPassword.clone();
        }
    }

    @Override
    public void unregister(long id) {
        if (accountEntries.containsKey(id)) {
            accountEntries.remove(id);
        }
    }

    /**
     * Persistent Entity for the Account.
     */
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    private class AccountEntry {

        public long id;
        public String username;
        public String email;
        public byte[] encryptedPassword;
        public String salt;
        public ZonedDateTime lastLoggedIn;
    }
}
