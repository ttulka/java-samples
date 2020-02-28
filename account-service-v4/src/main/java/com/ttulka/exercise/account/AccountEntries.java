package com.ttulka.exercise.account;

import java.time.ZonedDateTime;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DAO for the Account.
 */
interface AccountEntries {

    long save(Entry entry);

    Optional<Entry> byId(long id);

    Optional<Entry> byUsername(String username);;

    void delete(long id);

    @RequiredArgsConstructor
    class Entry {

        public final Long id;
        public final @NonNull String username;
        public final @NonNull String email;
        public final @NonNull byte[] encryptedPassword;
        public final @NonNull String salt;
        public final ZonedDateTime lastLoggedIn;
    }
}
