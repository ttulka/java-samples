package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

/**
 * Factory for the Account.
 */
interface AccountFactory {

    Account newAccount(String username, String email, String password);

    Account newAccount(long id, String username, String email, byte[] encryptedPassword, String salt, ZonedDateTime lastLoggedIn);
}
