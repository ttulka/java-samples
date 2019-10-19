package com.ttulka.exercise.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.ttulka.exercise.Account;
import com.ttulka.exercise.AccountNotFoundException;
import com.ttulka.exercise.AccountServiceInterface;
import com.ttulka.exercise.InvalidLoginException;
import com.ttulka.exercise.PersistenceInterface;
import com.ttulka.exercise.UsernameAlreadyExistsException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersistentAccountService implements AccountServiceInterface {

    private final PersistenceInterface accountRepo;

    @Override
    public Account login(@NonNull String username, @NonNull String password) {
        Account account = accountRepo.findByName(username);

        if (account == null) {
            throw new AccountNotFoundException(username);
        }
        if (!account.canLogin(password)) {
            throw new InvalidLoginException("Wrong password.");
        }

        doLogin(account);
        accountRepo.save(account);
        return account;
    }

    private void doLogin(Account account) {
        account.setLastLogin(ZonedDateTime.now());
    }

    @Override
    public Account register(@NonNull String username, @NonNull String email, @NonNull String password) {
        if (accountRepo.findByName(username) != null) {
            throw new UsernameAlreadyExistsException(username);
        }
        Account account = new Account(username, email, password);
        doLogin(account);
        accountRepo.save(account);
        return account;
    }

    @Override
    public void deleteAccount(@NonNull String username) {
        Account account = accountRepo.findByName(username);
        if (account == null) {
            throw new AccountNotFoundException(username);
        }
        accountRepo.delete(account);
    }

    @Override
    public boolean hasLoggedInSince(@NonNull String username, @NonNull Date date) {
        Account account = accountRepo.findByName(username);
        if (account == null) {
            throw new AccountNotFoundException(username);
        }
        return account.hasLoggedInSince(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public boolean hasLoggedInSince(Date date) {
        throw new UnsupportedOperationException("Use 'hasLoggedInSince(String, Date)' instead.");
    }
}
