package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Repository for the Account.
 */
interface AccountRegistry {

    long register(String username, String email, byte[] encryptedPassword, String salt, ZonedDateTime lastLoggedIn);

    Optional<Account> byId(long id);

    Optional<Account> byUsername(String username);

    void updateLastLoggedIn(long id, ZonedDateTime lastLoggedIn);

    void changeEncryptedPassword(long id, byte[] encryptedPassword);

    void unregister(long id);
}
