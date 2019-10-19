package com.ttulka.exercise;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * The encryption can be very simple, we don't put much emphasis on the encryption algorithm.
 */
@Getter
@EqualsAndHashCode(of = "username")
public class Account {

    private Long id;

    private String username;

    private String email;

    private byte[] encryptedPassword;

    private String salt;

    private ZonedDateTime lastLogin;

    public Account(@NonNull String username, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.email = email;
        this.salt = String.valueOf(new Random().nextInt());
        this.encryptedPassword = encryptedPassword(password, this.salt);
    }

    public Account(long id, @NonNull String username, @NonNull String email,
                   @NonNull byte[] encryptedPassword, @NonNull String salt,
                   ZonedDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.lastLogin = lastLogin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean canLogin(@NonNull String password) {
        return Arrays.equals(encryptedPassword, encryptedPassword(password, salt));
    }

    public boolean hasLoggedInSince(@NonNull ZonedDateTime date) {
        return lastLogin != null && date.isBefore(lastLogin);
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    private byte[] encryptedPassword(String password, String salt) {
        return String.valueOf((password + salt).hashCode()).getBytes(StandardCharsets.UTF_8);
    }
}
