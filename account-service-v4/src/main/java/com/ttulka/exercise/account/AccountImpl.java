package com.ttulka.exercise.account;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Random;

import com.ttulka.exercise.account.exception.AccountAlreadyRegisteredException;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(of = "username")
class AccountImpl implements Account {

    private final String username;
    private final String email;

    private byte[] encryptedPassword;
    private String salt;
    private ZonedDateTime lastLoggedIn;

    private Long id;

    private final AccountEntries entries;

    AccountImpl(@NonNull String username, @NonNull String email, @NonNull String password,
                @NonNull AccountEntries entries) {
        this.username = username;
        this.email = email;
        this.salt = String.valueOf(new Random().nextInt());
        this.encryptedPassword = encryptedPassword(password, this.salt);
        this.entries = entries;
    }

    AccountImpl(long id, @NonNull String username, @NonNull String email,
                @NonNull byte[] encryptedPassword, @NonNull String salt,
                ZonedDateTime lastLoggedIn,
                @NonNull AccountEntries entries) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.lastLoggedIn = lastLoggedIn;
        this.entries = entries;
    }

    @Override
    public boolean canLogin(@NonNull String password) {
        return Arrays.equals(encryptedPassword, encryptedPassword(password, salt));
    }

    @Override
    public boolean hasLoggedInSince(@NonNull ZonedDateTime date) {
        return lastLoggedIn != null && date.isBefore(lastLoggedIn);
    }

    @Override
    public void login() {
        lastLoggedIn = ZonedDateTime.now();
        update();
    }

    @Override
    public void changePassword(@NonNull String newPassword) {
        encryptedPassword = encryptedPassword(newPassword, salt);
        update();
    }

    @Override
    public void register() {
        if (isRegistered()) {
            throw new AccountAlreadyRegisteredException(username);
        }
        id = entries.save(new AccountEntries.AccountEntry(
                null, username, email, encryptedPassword, salt, lastLoggedIn));
    }

    @Override
    public void unregister() {
        if (isRegistered()) {
            entries.delete(id);
        }
    }

    private void update() {
        if (isRegistered()) {
            entries.save(new AccountEntries.AccountEntry(
                    id, username, email, encryptedPassword, salt, lastLoggedIn));
        }
    }

    private boolean isRegistered() {
        return id != null;
    }

    private byte[] encryptedPassword(String password, String salt) {
        return String.valueOf((password + salt).hashCode()).getBytes(StandardCharsets.UTF_8);
    }
}
