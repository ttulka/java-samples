package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import com.ttulka.exercise.account.exception.AccountNotFoundException;
import com.ttulka.exercise.account.exception.InvalidLoginException;
import com.ttulka.exercise.account.exception.UsernameAlreadyExistsException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Account register(@NonNull String username, @NonNull String password, @NonNull String email) {
        if (repository.byUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
        Account account = new Account(username, password, email);
        account.login();
        repository.save(account);
        return account;
    }

    @Override
    public Account login(@NonNull String username, @NonNull String password) {
        Account account = repository.byUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        if (!account.canLogin(password)) {
            throw new InvalidLoginException("Wrong password.");
        }
        account.login();
        repository.save(account);
        return account;
    }

    @Override
    public boolean hasLoggedInSince(@NonNull String username, @NonNull ZonedDateTime date) {
        return repository.byUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(username))
                .hasLoggedInSince(date);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Account account = repository.byUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        if (!account.canLogin(oldPassword)) {
            throw new InvalidLoginException("Wrong password.");
        }
        account.changePassword(newPassword);
        repository.save(account);
    }

    @Override
    public void unregister(@NonNull String username) {
        Account account = repository.byUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        repository.delete(account);
    }
}
