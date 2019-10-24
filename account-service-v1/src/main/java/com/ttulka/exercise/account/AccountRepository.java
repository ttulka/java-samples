package com.ttulka.exercise.account;

import java.util.Optional;

/**
 * Repository for the Account.
 */
interface AccountRepository {

    void save(Account account);

    Optional<Account> byId(long id);

    Optional<Account> byUsername(String username);

    void delete(Account account);
}
