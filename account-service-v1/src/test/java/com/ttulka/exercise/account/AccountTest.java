package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void invalid_username_raises_an_error() {
        assertThrows(Exception.class, () ->
                new Account(null, "test@example.com", "pwd1"));
    }

    @Test
    void invalid_email_raises_an_error() {
        assertThrows(Exception.class, () ->
                new Account("test", null, "pwd1"));
    }

    @Test
    void invalid_password_raises_an_error() {
        assertThrows(Exception.class, () ->
                new Account("test", "test@example.com", null));
    }

    @Test
    void encrypted_password_is_created() {
        Account account = new Account("test", "test@example.com", "pwd1");

        assertThat(account.getEncryptedPassword()).isNotNull();
    }

    @Test
    void salt_is_generated() {
        Account account = new Account("test", "test@example.com", "pwd1");

        assertThat(account.getSalt()).isNotNull();
    }

    @Test
    void can_login_with_a_valid_password() {
        Account account = new Account("test", "test@example.com", "pwd1");

        assertThat(account.canLogin("pwd1")).isTrue();
    }

    @Test
    void cannot_login_with_an_invalid_password() {
        Account account = new Account("test", "test@example.com", "pwd1");

        assertThat(account.canLogin("xxx")).isFalse();
    }

    @Test
    void valid_password_is_changed() {
        Account account = new Account("test", "test@example.com", "pwd1");
        account.changePassword("updated");

        assertThat(account.canLogin("pwd1")).isFalse();
        assertThat(account.canLogin("updated")).isTrue();
    }

    @Test
    void login_sets_last_logged_in() {
        Account account = new Account("test", "test@example.com", "pwd1");
        account.login();

        assertThat(account.getLastLoggedIn()).isNotNull();
    }

    @Test
    void has_logged_in_since_before_login() {
        Account account = new Account("test", "test@example.com", "pwd1");
        account.login();

        assertThat(account.hasLoggedInSince(ZonedDateTime.now().minusSeconds(1))).isTrue();
    }

    @Test
    void has_not_logged_in_since_after_login() {
        Account account = new Account("test", "test@example.com", "pwd1");
        account.login();

        assertThat(account.hasLoggedInSince(ZonedDateTime.now().plusSeconds(1))).isFalse();
    }
}
