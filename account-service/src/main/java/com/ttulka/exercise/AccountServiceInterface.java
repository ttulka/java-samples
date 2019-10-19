package com.ttulka.exercise;

import java.util.Date;

/**
 * Service for account management.
 */
public interface AccountServiceInterface {

    /**
     * Logs in the user, if the username exists and the password is correct. Updates the last setLastLogin date
     *
     * @param username the User's name
     * @param password the clear text password
     * @return the logged-in account
     * @throws AccountServiceException if any errors occur
     */
    Account login(String username, String password);

    /**
     * Registers a new Account, if the username doesn't exist yet and logs-in the user.
     *
     * @param username the User's name
     * @param email    the email address of the user
     * @param password the clear text password
     * @return the newly registered Account
     * @throws AccountServiceException if any errors occur
     */
    Account register(String username, String email, String password);

    /**
     * Deletes an Account, if the user exist.
     *
     * @param username the User's name
     * @throws AccountServiceException if any errors occur
     */
    void deleteAccount(String username);

    /**
     * Checks if a user has logged in since a provided timestamp.
     *
     * @param username the User's name
     * @param date the timestamp
     * @return true if the user has logged in since the provided timestamp, else false.
     * @throws AccountServiceException if any error occurs
     */
    boolean hasLoggedInSince(String username, Date date);

    /**
     * Checks if a user has logged in since a provided timestamp.
     *
     * @param date
     * @return true if the user has logged in since the provided timestamp, else false.
     * @throws AccountServiceException if any error occurs
     * @deprecated This method throws always UnsupportedOperationException. Please use {@link #hasLoggedInSince(String, Date)} instead.
     */
    @Deprecated
    boolean hasLoggedInSince(Date date);
}
