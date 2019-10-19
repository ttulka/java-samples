## Assumptions

As no "story", use-case of any other domain description was provided,
I stick with the API as the only input for the implementation. This means several assumptions:

- A.1: The API must not be changed (as far as possible).
    - Possibly there are already clients using it.
- A.2: The API might be extended if necessary.

Without A.1 several improvements are to be considered:

- AI.1: Change the artifact ID to be more domain-oriented.
    - For example: `ttulka-account`, or better `ttulka-user/customer`
        (as the Account aggregate is probably a part of a User/Customer service).

- AI.2: Change the Java package name accordingly.
    - For example: `com.ttulka.account`, or better `com.ttulka.user/customer` (see AI.1)

- AI.3: Change the interface names to be more domain-oriented.
    - For example: `AccountServiceInterface` => `AccountService`, or maybe just `Accounts`
        (to get rid of any technical fluff from the domain API),
        `PersistenceInterface` => `AccountRepository` or `AccountEntities` (stay tuned), `AccountServiceException` => `AccountException`.

- AI.4: Create an interface `Account` as a part of the API.
    - Create a `PersistentAccount` class implementing that interface as a part of the impl module.
    - This enables separation of the API and persistence and reduces the anemic domain model.

- AI.5: Get rid of the old Java API, concretely `java.util.Date`.
    - Use the new API in `java.time`: `LocalDateTime` or better `ZonedDateTime`.

- AI.6: Change the return type of *find* methods to `Optional<Account>`.

- AI.7: Change `PersistenceInterface.findByName` to `PersistenceInterface.findByUsername` to be descriptive and consistent.

### The necessary breaking change

- BC.1: The method `boolean hasLoggedInSince(Date date)` has no place in the domain service as it is.
    Possible fixes:
    - BC.1.1: Change the signature to `boolean hasLoggedInSince(String username, Date date)`.
    - BC.1.2: Move the method into the `Account` object.

As the task says

> create the implementation for these interfaces

and the `Account` is no real domain object so far (stay tuned), I choose the BC.1.1 over BC.1.2.

## Implementation notes

Most of things I have done are probably not as you expected, but  otherwise it would have been pretty boring :-)

First of all, I decided to stick with the current API as provided, because working with legacy code is hard and something you obviously need to do every day.
The only exception is the `hasLoggedInSince` method, which makes no sense without an user/account (is it a bug or a test?).

Only interesting code is in `InMemoryAccountRepository`.
    What I did there is an overkill, over-engineering etc. and totally unnecessary for an *in memory* implementation.

I agree.

What I tried to do is the first step to get rid of an anemic model represented by `Account` class.

The repository uses a low-level repository, or better said a DAO (currently implemented with a `HashMap`).

The second level repository doesn't work with `Account` domain objects, in fact it works with no objects at all:
    `AccountEntry` is nothing more than a struct of data. You can imagine it annotated with JPA's `@Entity`
    and instead of `HashMap` an `EntityManager` or a Spring's `CrudRepository` etc.

This enables the next step: moving methods `save` and `delete` to the `Account` itself,
    giving them better business names like `open` and `close` or whatever is used for opening and closing an account.

Moving the `Account`'s API into an interface and implementation to a `PersistentAccount` enables
    getting rid of all the getters/setters as they are no part of the domain and make the object dumb.
    The `Account` becomes a real domain object and has nothing to do with persistence, is not sent over wire etc.
    (that's why no `Serializable` is needed).

The object would look as follows (simplified):

```java
@AllArgsConstructor
public class PersistentAccount implements Account {

    private Long id;
    private String username;
    private String email;
    ...

    private final AccountEntries entries;

    @Override
    public void open() {
        AccountEntry ae = entries.save(new AccountEntry(null, username, email, ...));
        this.id = ae.id;
    }

    @Override
    public void close() {
        entries.deleteById(this.id);
    }

    ...
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
class AccountEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long id;
    @Column(unique = true, nullable = false)
    public String username;
    @Column(nullable = false)
    public String email;
    ...
}
```

I know an `Account` with getters/setters and `Account` objects saved in the `HashMap` would be just fine,
    at least in this case, but also uninteresting.

This is a different programming style, something that could be controversial and must be discussed and agreed in the whole team,
    as only one programming style should be followed by all team members.

## Open questions

- Q.1: Is the account's ID really a business concept? Or just a leak of persistence into the API?

## Future improvements

- I.1: Separate the API from the implementation.
    - A new module (artifact) can be created.
    - The package name can then stay the same (no additional `impl` package),
        which means not all the classes must be public.
        - Non public classes cannot be accessed outside the package,
            which means those are accessible only from within the aggregate
            => no temptation to use them in an inappropriate place.

- I.2: Validation of business invariants.
    - Only non-null checks implemented so far.

---

This project includes two interfaces: AccountServiceInterface and PersistenceInterface.
Your task is to create the implementation for these interfaces, according to the JavaDoc.
We expect you to also write unit tests and we value clean code. You can add any library you want.
If anything is unclear, make your own decision. Just as you would in your daily work.