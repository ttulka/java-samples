package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import com.ttulka.exercise.account.exception.AccountNotFoundException;
import com.ttulka.exercise.account.exception.InvalidLoginException;
import com.ttulka.exercise.account.exception.UsernameAlreadyExistsException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountsImpl implements Accounts {

    private final AccountEntries entries;

    @Override
    public Account register(@NonNull String username, @NonNull String password, @NonNull String email) {
        if (entries.byUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
        Account account = new AccountImpl(username, password, email, entries);
        account.register();
        account.login();
        return account;
    }

    @Override
    public Account login(@NonNull String username, @NonNull String password) {
        Account account = registeredAccountByUsername(username);
        if (!account.canLogin(password)) {
            throw new InvalidLoginException("Wrong password.");
        }
        account.login();
        return account;
    }

    @Override
    public boolean hasLoggedInSince(@NonNull String username, @NonNull ZonedDateTime date) {
        return registeredAccountByUsername(username)
                .hasLoggedInSince(date);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Account account = registeredAccountByUsername(username);
        if (!account.canLogin(oldPassword)) {
            throw new InvalidLoginException("Wrong password.");
        }
        account.changePassword(newPassword);
    }

    @Override
    public void unregister(@NonNull String username) {
        registeredAccountByUsername(username)
                .unregister();
    }

    private Account registeredAccountByUsername(String username) {
        return entries.byUsername(username)
                .map(entry ->new AccountImpl(
                        entry.id, entry.username, entry.email, entry.encryptedPassword, entry.salt, entry.lastLoggedIn,
                        entries))
                .orElseThrow(() -> new AccountNotFoundException(username));
    }
}
