package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

class AccountRepositoryInMemory implements AccountRepository {

    private final Map<Long, AccountEntry> accountEntries = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public void save(@NonNull Account account) {
        if (account.getId() != null) {
            AccountEntry entry = accountEntries.get(account.getId());
            entry.username = account.getUsername();
            entry.email = account.getEmail();
            entry.encryptedPassword = account.getEncryptedPassword().clone();
            entry.salt = account.getSalt();
            entry.lastLoggedIn = account.getLastLoggedIn();

        } else {
            long id = idSequence.incrementAndGet();
            accountEntries.put(id, new AccountEntry(
                    id,
                    account.getUsername(),
                    account.getEmail(),
                    account.getEncryptedPassword().clone(),
                    account.getSalt(),
                    account.getLastLoggedIn()));
            account.setId(id);
        }
    }

    @Override
    public Optional<Account> byId(long id) {
        if (accountEntries.containsKey(id)) {
            AccountEntry entry = accountEntries.get(id);
            return Optional.of(new Account(
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
    public Optional<Account> byUsername(@NonNull String username) {
        return accountEntries.values().stream()
                .filter(entry -> username.equals(entry.username))
                .findAny()
                .map(entry -> new Account(
                        entry.id,
                        entry.username,
                        entry.email,
                        entry.encryptedPassword.clone(),
                        entry.salt,
                        entry.lastLoggedIn
                ));
    }

    @Override
    public void delete(@NonNull Account account) {
        if (accountEntries.containsKey(account.getId())) {
            accountEntries.remove(account.getId());
        }
    }

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
