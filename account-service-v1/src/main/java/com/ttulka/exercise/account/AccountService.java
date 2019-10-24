package com.ttulka.exercise.account;

import java.time.ZonedDateTime;

import com.ttulka.exercise.account.exception.AccountServiceException;

/**
 * Service for the Account.
 */
public interface AccountService {

    /**
     * Registers a new Account, if the username doesn't exist yet and logs-in the user.
     *
     * @param username the username
     * @param password the clear text password
     * @param email    the email address of the user
     * @return the newly registered Account
     * @throws AccountServiceException if any errors occur
     */
    Account register(String username, String password, String email);

    /**
     * Logs in the user, if the username exists and the password is correct. Updates the last setLastLogin date
     *
     * @param username the username
     * @param password the clear text password
     * @return the logged-in account
     * @throws AccountServiceException if any errors occur
     */
    Account login(String username, String password);

    /**
     * Checks if a user has logged in since a provided timestamp.
     *
     * @param username the username
     * @param date     the timestamp
     * @return true if the user has logged in since the provided timestamp, else false.
     * @throws AccountServiceException if any error occurs
     */
    boolean hasLoggedInSince(String username, ZonedDateTime date);

    /**
     * Changes the Account password.
     *
     * @param username    the username
     * @param oldPassword the old password
     * @param newPassword the new password
     * @throws AccountServiceException if any error occurs
     */
    void changePassword(String username, String oldPassword, String newPassword);

    /**
     * Deletes an Account, if the user exist.
     *
     * @param username the username
     * @throws AccountServiceException if any errors occur
     */
    void unregister(String username);
}
