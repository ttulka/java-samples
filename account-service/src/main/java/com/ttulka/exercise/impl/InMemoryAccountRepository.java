package com.ttulka.exercise.impl;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import com.ttulka.exercise.Account;
import com.ttulka.exercise.PersistenceInterface;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

public class InMemoryAccountRepository implements PersistenceInterface {

    private final Map<Long, AccountEntry> accountEntries = new HashMap<>();

    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public void save(@NonNull Account account) {
        Long id = account.getId();
        if (id == null) {
            id = idSequence.incrementAndGet();
            account.setId(id);
        }
        accountEntries.put(id,
           new AccountEntry(
               id,
               account.getUsername(),
               account.getEmail(),
               account.getEncryptedPassword().clone(),
               account.getSalt(),
               account.getLastLogin()
           ));
    }

    @Override
    public Account findById(long id) {
        if (accountEntries.containsKey(id)) {
            AccountEntry entry = accountEntries.get(id);
            return new Account(
                entry.id,
                entry.username,
                entry.email,
                entry.encryptedPassword.clone(),
                entry.salt,
                entry.lastLogin
            );
        }
        return null;
    }

    @Override
    public Account findByName(@NonNull String username) {
        return accountEntries.values().stream()
                .filter(entry -> username.equals(entry.username))
                .findAny()
                .map(entry -> new Account(
                    entry.id,
                    entry.username,
                    entry.email,
                    entry.encryptedPassword.clone(),
                    entry.salt,
                    entry.lastLogin
                ))
                .orElse(null);
    }

    @Override
    public void delete(@NonNull Account account) {
        accountEntries.remove(account.getId());
    }

    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    private class AccountEntry {

        public long id;
        public String username;
        public String email;
        public byte[] encryptedPassword;
        public String salt;
        public ZonedDateTime lastLogin;    }
}
