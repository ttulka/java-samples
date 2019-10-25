package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import com.ttulka.exercise.account.exception.AccountAlreadyRegisteredException;

/**
 * Aggregate for the Account.
 */
public interface Account {

    /**
     * Registers the Account.
     * @throws AccountAlreadyRegisteredException when registering an already registered Account
     */
    void register();

    /**
     * Unregisters the Account.
     */
    void unregister();

    /**
     * Returns whether login can be performed with the provided password.
     *
     * @return true if login can be performed with the provided password, otherwise false
     */
    boolean canLogin(String password);

    /**
     * Performs login.
     */
    void login();

    /**
     * Returns whether logged in since the provided date.
     * @param date the date too check
     * @return true if logged in since the provided date, otherwise false
     */
    boolean hasLoggedInSince(ZonedDateTime date);

    /**
     * Changes the Account password.
     *
     * @param newPassword the new password of the Account
     */
    void changePassword(String newPassword);
}
