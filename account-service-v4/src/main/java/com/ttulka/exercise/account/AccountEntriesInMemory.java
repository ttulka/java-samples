package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

class AccountEntriesInMemory implements AccountEntries {

    private final Map<Long, AccountEntity> accountEntries = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public long save(@NonNull AccountEntries.AccountEntry entry) {
        long id = entry.id != null ? entry.id : idSequence.incrementAndGet();
        accountEntries.put(id, new AccountEntity(id, entry.username, entry.email, entry.encryptedPassword.clone(), entry.salt, entry.lastLoggedIn));
        return id;
    }

    @Override
    public Optional<AccountEntry> byId(long id) {
        if (accountEntries.containsKey(id)) {
            AccountEntity entry = accountEntries.get(id);
            return Optional.of(new AccountEntry(
                    entry.id,
                    entry.username,
                    entry.email,
                    entry.encryptedPassword.clone(),
                    entry.salt,
                    entry.lastLoggedIn
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<AccountEntry> byUsername(@NonNull String username) {
        return accountEntries.values().stream()
                .filter(entry -> username.equals(entry.username))
                .findAny()
                .map(entry -> new AccountEntry(
                        entry.id,
                        entry.username,
                        entry.email,
                        entry.encryptedPassword.clone(),
                        entry.salt,
                        entry.lastLoggedIn
                ));
    }

    @Override
    public void delete(long id) {
        if (accountEntries.containsKey(id)) {
            accountEntries.remove(id);
        }
    }

    /**
     * Persistent Entity for the Account.
     */
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    private class AccountEntity {

        public long id;
        public String username;
        public String email;
        public byte[] encryptedPassword;
        public String salt;
        public ZonedDateTime lastLoggedIn;
    }
}
