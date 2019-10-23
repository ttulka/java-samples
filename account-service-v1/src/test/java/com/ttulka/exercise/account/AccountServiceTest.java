package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.ttulka.exercise.account.exception.AccountNotFoundException;
import com.ttulka.exercise.account.exception.InvalidLoginException;
import com.ttulka.exercise.account.exception.UsernameAlreadyExistsException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    @Test
    void registered_account_with_correct_password_can_log_in() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        Account account = accountService.login(username, "pwd1");
        assertThat(account).isNotNull();
    }

    @Test
    void once_registered_a_username_cannot_be_registered_again() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(UsernameAlreadyExistsException.class, () ->
                accountService.register(username, "test@example.com", "pwd1"));
    }

    @Test
    void non_existing_account_cannot_log_in() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login("this user does not exist", "password"));
    }

    @Test
    void existing_account_with_wrong_password_cannot_log_in() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(InvalidLoginException.class, () ->
                accountService.login(username, "wrong password"));
    }

    @Test
    void deleted_account_cannot_log_in() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        accountService.unregister(username);

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login(username, "pwd1"));
    }

    @Test
    void account_is_logged_in_since_before_logged_in_for_the_last_time() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime beforeLogin = ZonedDateTime.now().minusSeconds(1);
        accountService.login(username, "pwd1");

        boolean loggedIn = accountService.hasLoggedInSince(username, beforeLogin);
        assertThat(loggedIn).isTrue();
    }

    @Test
    void account_not_logged_in_since_after_logged_in_for_the_last_time() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        accountService.login(username, "pwd1");
        ZonedDateTime afterLogin = ZonedDateTime.now().plusSeconds(1);

        boolean loggedIn = accountService.hasLoggedInSince(username, afterLogin);
        assertThat(loggedIn).isFalse();
    }

    @Test
    void account_not_logged_in_since_now_after_been_registered() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime afterRegistration = ZonedDateTime.now().plusSeconds(1);

        boolean loggedInAfter = accountService.hasLoggedInSince(username, afterRegistration);
        assertThat(loggedInAfter).isFalse();
    }

    @Test
    void password_is_changed() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        accountService.changePassword(username, "pwd1", "updated");

        Account account = accountService.login(username, "updated");
        assertThat(account).isNotNull();
    }

    @Test
    void change_password_catches_an_invalid_old_password() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(InvalidLoginException.class, () ->
                accountService.changePassword(username, "wrong password", "updated"));
    }

    @Test
    void old_password_invalid_after_changed() {
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryInMemory());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        accountService.changePassword(username, "pwd1", "updated");

        assertThrows(InvalidLoginException.class, () -> accountService.login(username, "pwd1"));
    }
}
