package com.ttulka.exercise.account;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Random;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Aggregate for the Account.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "username")
public class Account {

    private final String username;
    private final String email;

    private byte[] encryptedPassword;
    private String salt;
    private ZonedDateTime lastLoggedIn;

    private Long id;

    Account(@NonNull String username, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.email = email;
        this.salt = String.valueOf(new Random().nextInt());
        this.encryptedPassword = encryptedPassword(password, this.salt);
    }

    Account(long id, @NonNull String username, @NonNull String email,
            @NonNull byte[] encryptedPassword, @NonNull String salt,
            ZonedDateTime lastLoggedIn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.lastLoggedIn = lastLoggedIn;
    }

    public boolean canLogin(@NonNull String password) {
        return Arrays.equals(encryptedPassword, encryptedPassword(password, salt));
    }

    public boolean hasLoggedInSince(@NonNull ZonedDateTime date) {
        return lastLoggedIn != null && date.isBefore(lastLoggedIn);
    }

    public void login() {
        lastLoggedIn = ZonedDateTime.now();
    }

    public void changePassword(@NonNull String newPassword) {
        encryptedPassword = encryptedPassword(newPassword, salt);
    }

    private byte[] encryptedPassword(String password, String salt) {
        return String.valueOf((password + salt).hashCode()).getBytes(StandardCharsets.UTF_8);
    }
}
