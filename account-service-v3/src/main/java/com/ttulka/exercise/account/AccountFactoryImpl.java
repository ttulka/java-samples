package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AccountFactoryImpl implements AccountFactory {

    private final AccountEntries entries;

    @Override
    public Account newAccount(String username, String email, String password) {
        return new AccountImpl(username, email, password, entries);
    }

    @Override
    public Account newAccount(long id, String username, String email, byte[] encryptedPassword, String salt, ZonedDateTime lastLoggedIn) {
        return new AccountImpl(id, username, email, encryptedPassword, salt, lastLoggedIn, entries);
    }
}
