package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.ttulka.exercise.account.exception.AccountNotFoundException;
import com.ttulka.exercise.account.exception.InvalidLoginException;
import com.ttulka.exercise.account.exception.UsernameAlreadyExistsException;

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

    @Test
    void registered_account_with_correct_password_can_log_in() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        Account account = accountService.login(username, "pwd1");
        assertThat(account).isNotNull();
    }

    @Test
    void once_registered_a_username_cannot_be_registered_again() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(UsernameAlreadyExistsException.class, () ->
                accountService.register(username, "test@example.com", "pwd1"));
    }

    @Test
    void non_existing_account_cannot_log_in() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login("this user does not exist", "password"));
    }

    @Test
    void existing_account_with_wrong_password_cannot_log_in() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(InvalidLoginException.class, () ->
                accountService.login(username, "wrong password"));
    }

    @Test
    void deleted_account_cannot_log_in() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        accountService.unregister(username);

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login(username, "pwd1"));
    }

    @Test
    void account_is_logged_in_since_before_logged_in_for_the_last_time() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime beforeLogin = ZonedDateTime.now().minusSeconds(1);
        accountService.login(username, "pwd1");

        boolean loggedIn = accountService.hasLoggedInSince(username, beforeLogin);
        assertThat(loggedIn).isTrue();
    }

    @Test
    void account_not_logged_in_since_after_logged_in_for_the_last_time() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        accountService.login(username, "pwd1");
        ZonedDateTime afterLogin = ZonedDateTime.now().plusSeconds(1);

        boolean loggedIn = accountService.hasLoggedInSince(username, afterLogin);
        assertThat(loggedIn).isFalse();
    }

    @Test
    void account_not_logged_in_since_now_after_been_registered() {
        AccountService accountService = new RegisteredAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime afterRegistration = ZonedDateTime.now().plusSeconds(1);

        boolean loggedInAfter = accountService.hasLoggedInSince(username, afterRegistration);
        assertThat(loggedInAfter).isFalse();
    }
}
