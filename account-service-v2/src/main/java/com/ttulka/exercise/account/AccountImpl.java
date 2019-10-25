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

    private Long registrationId;

    private final AccountRegistry registry;

    AccountImpl(@NonNull String username, @NonNull String email, @NonNull String password,
                @NonNull AccountRegistry registry) {
        this.username = username;
        this.email = email;
        this.salt = String.valueOf(new Random().nextInt());
        this.encryptedPassword = encryptedPassword(password, this.salt);
        this.registry = registry;
    }

    AccountImpl(long registrationId, @NonNull String username, @NonNull String email,
                @NonNull byte[] encryptedPassword, @NonNull String salt,
                ZonedDateTime lastLoggedIn,
                @NonNull AccountRegistry registry) {
        this.registrationId = registrationId;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.lastLoggedIn = lastLoggedIn;
        this.registry = registry;
    }

    @Override
    public void register() {
        if (isRegistered()) {
            throw new AccountAlreadyRegisteredException(username);
        }
        registrationId = registry.register(username, email, encryptedPassword, salt, lastLoggedIn);
    }

    @Override
    public void unregister() {
        if (isRegistered()) {
            registry.unregister(registrationId);
        }
    }

    @Override
    public void login() {
        lastLoggedIn = ZonedDateTime.now();
        if (isRegistered()) {
            registry.updateLastLoggedIn(registrationId, lastLoggedIn);
        }
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
    public void changePassword(@NonNull String newPassword) {
        encryptedPassword = encryptedPassword(newPassword, salt);
        if (isRegistered()) {
            registry.changeEncryptedPassword(registrationId, encryptedPassword);
        }
    }

    private boolean isRegistered() {
        return registrationId != null;
    }

    private byte[] encryptedPassword(String password, String salt) {
        return String.valueOf((password + salt).hashCode()).getBytes(StandardCharsets.UTF_8);
    }
}
