package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.ttulka.exercise.account.exception.AccountNotFoundException;
import com.ttulka.exercise.account.exception.InvalidLoginException;
import com.ttulka.exercise.account.exception.UsernameAlreadyExistsException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountsTest {

    @Test
    void registered_account_with_correct_password_can_log_in() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        Account account = accounts.login(username, "pwd1");
        assertThat(account).isNotNull();
    }

    @Test
    void once_registered_a_username_cannot_be_registered_again() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        assertThrows(UsernameAlreadyExistsException.class, () ->
                accounts.register(username, "test@example.com", "pwd1"));
    }

    @Test
    void non_existing_account_cannot_log_in() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        assertThrows(AccountNotFoundException.class, () ->
                accounts.login("this user does not exist", "password"));
    }

    @Test
    void existing_account_with_wrong_password_cannot_log_in() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        assertThrows(InvalidLoginException.class, () ->
                accounts.login(username, "wrong password"));
    }

    @Test
    void deleted_account_cannot_log_in() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");

        accounts.unregister(username);

        assertThrows(AccountNotFoundException.class, () ->
                accounts.login(username, "pwd1"));
    }

    @Test
    void account_is_logged_in_since_before_logged_in_for_the_last_time() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");
        ZonedDateTime beforeLogin = ZonedDateTime.now().minusSeconds(1);
        accounts.login(username, "pwd1");

        boolean loggedIn = accounts.hasLoggedInSince(username, beforeLogin);
        assertThat(loggedIn).isTrue();
    }

    @Test
    void account_not_logged_in_since_after_logged_in_for_the_last_time() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");
        accounts.login(username, "pwd1");
        ZonedDateTime afterLogin = ZonedDateTime.now().plusSeconds(1);

        boolean loggedIn = accounts.hasLoggedInSince(username, afterLogin);
        assertThat(loggedIn).isFalse();
    }

    @Test
    void account_not_logged_in_since_now_after_been_registered() {
        Accounts accounts = new AccountsImpl(new InMemoryAccountRegistry());

        String username = UUID.randomUUID().toString();
        accounts.register(username, "test@example.com", "pwd1");
        ZonedDateTime afterRegistration = ZonedDateTime.now().plusSeconds(1);

        boolean loggedInAfter = accounts.hasLoggedInSince(username, afterRegistration);
        assertThat(loggedInAfter).isFalse();
    }
}
