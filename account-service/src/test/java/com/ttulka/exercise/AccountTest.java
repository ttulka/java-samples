package com.ttulka.exercise;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import com.ttulka.exercise.impl.InMemoryAccountRepository;
import com.ttulka.exercise.impl.PersistentAccountService;

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
    void registered_account_with_correct_password_can_log_in() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        Account account = accountService.login(username, "pwd1");
        assertThat(account).isNotNull();
    }

    @Test
    void once_registered_a_username_cannot_be_registered_again() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(UsernameAlreadyExistsException.class, () ->
                accountService.register(username, "test@example.com", "pwd1"));
    }

    @Test
    void non_existing_account_cannot_log_in() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login("this user does not exist", "password"));
    }

    @Test
    void existing_account_with_wrong_password_cannot_log_in() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        assertThrows(InvalidLoginException.class, () ->
                accountService.login(username, "wrong password"));
    }

    @Test
    void deleted_account_cannot_log_in() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");

        accountService.deleteAccount(username);

        assertThrows(AccountNotFoundException.class, () ->
                accountService.login(username, "pwd1"));
    }

    @Test
    void account_is_logged_in_since_before_logged_in_for_the_last_time() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime beforeLogin = ZonedDateTime.now().minusSeconds(1);
        accountService.login(username, "pwd1");

        boolean loggedIn = accountService.hasLoggedInSince(username, Date.from(beforeLogin.toInstant()));
        assertThat(loggedIn).isTrue();
    }

    @Test
    void account_not_logged_in_since_after_logged_in_for_the_last_time() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        accountService.login(username, "pwd1");
        ZonedDateTime afterLogin = ZonedDateTime.now().plusSeconds(1);

        boolean loggedIn = accountService.hasLoggedInSince(username, Date.from(afterLogin.toInstant()));
        assertThat(loggedIn).isFalse();
    }

    @Test
    void account_not_logged_in_since_now_after_been_registered() {
        AccountServiceInterface accountService = new PersistentAccountService(new InMemoryAccountRepository());

        String username = UUID.randomUUID().toString();
        accountService.register(username, "test@example.com", "pwd1");
        ZonedDateTime afterRegistration = ZonedDateTime.now().plusSeconds(1);

        boolean loggedInAfter = accountService.hasLoggedInSince(username, Date.from(afterRegistration.toInstant()));
        assertThat(loggedInAfter).isFalse();
    }
}
