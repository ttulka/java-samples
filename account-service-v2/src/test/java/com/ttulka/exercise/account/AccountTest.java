package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void invalid_username_raises_an_error() {
        assertThrows(Exception.class, () ->
                new AccountImpl(null, "test@example.com", "pwd1", new AccountRegistryInMemory()));
    }

    @Test
    void invalid_email_raises_an_error() {
        assertThrows(Exception.class, () ->
                new AccountImpl("test", null, "pwd1", new AccountRegistryInMemory()));
    }

    @Test
    void invalid_password_raises_an_error() {
        assertThrows(Exception.class, () ->
                new AccountImpl("test", "test@example.com", null, new AccountRegistryInMemory()));
    }

    @Test
    void can_login_with_a_valid_password() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());

        assertThat(account.canLogin("pwd1")).isTrue();
    }

    @Test
    void cannot_login_with_an_invalid_password() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());

        assertThat(account.canLogin("xxx")).isFalse();
    }

    @Test
    void valid_password_is_changed() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());
        account.changePassword("updated");

        assertThat(account.canLogin("pwd1")).isFalse();
        assertThat(account.canLogin("updated")).isTrue();
    }

    @Test
    void has_logged_in_since_before_login() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());
        account.login();

        assertThat(account.hasLoggedInSince(ZonedDateTime.now().minusSeconds(1))).isTrue();
    }

    @Test
    void has_not_logged_in_since_after_login() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());
        account.login();

        assertThat(account.hasLoggedInSince(ZonedDateTime.now().plusSeconds(1))).isFalse();
    }

    @Test
    void password_is_changed() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());
        account.changePassword("updated");

        assertThat(account.canLogin("updated")).isTrue();
    }

    @Test
    void old_password_invalid_after_changed() {
        Account account = new AccountImpl("test", "test@example.com", "pwd1", new AccountRegistryInMemory());
        account.changePassword("updated");

        assertThat(account.canLogin("pwd1")).isFalse();
    }
}
