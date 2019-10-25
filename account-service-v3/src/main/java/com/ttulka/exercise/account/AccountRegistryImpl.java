package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRegistryImpl implements AccountRegistry {

    private final AccountFactory factory;
    private final AccountEntries entries;

    @Override
    public Optional<Account> byId(long id) {
        return entries.byId(id)
                .map(entry -> factory.newAccount(
                        entry.id, entry.username, entry.email, entry.encryptedPassword, entry.salt, entry.lastLoggedIn));
    }

    @Override
    public Optional<Account> byUsername(String username) {
        return entries.byUsername(username)
                .map(entry -> factory.newAccount(
                        entry.id, entry.username, entry.email, entry.encryptedPassword, entry.salt, entry.lastLoggedIn));
    }

    @Override
    public void updateLastLoggedIn(long id, ZonedDateTime lastLoggedIn) {
        entries.byId(id)
                .ifPresent(entry -> entries.save(new AccountEntries.AccountEntry(
                        id, entry.username, entry.email, entry.encryptedPassword, entry.salt, lastLoggedIn)));
    }

    @Override
    public void changeEncryptedPassword(long id, byte[] encryptedPassword) {
        entries.byId(id)
                .ifPresent(entry -> entries.save(new AccountEntries.AccountEntry(
                        id, entry.username, entry.email, encryptedPassword, entry.salt, entry.lastLoggedIn)));
    }

    @Override
    public void unregister(long id) {
        entries.delete(id);
    }
}
