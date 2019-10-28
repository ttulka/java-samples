# DDD Exercise: The Account Service
Implement an Account service in DDD manner (OOP).
- All objects are strictly encapsulated.
- No leak of implementation details (persistence, inner state). 

## Implementation Notes

- `Account` and `Accounts` interfaces used as the abstraction for a client.

- `AccountEnties` interfaces introduced as the only one in-memory implementation invokes multiple implementations.
    - The interface is package-protected as not being part of the API.
    - In a real-world implementation there would be no need for an interface as this would violate the YAGNI principle.

```
[AccountImpl] ──────────┐
 ↑      ▼               │
 |  <Account>           ↓
 |      ↑        <AccountEntities> ◄ [AccountEntitiesInMemory]
 |  <Accounts>          ↑
 |      ▲               │
[AccountsImpl] ─────────┘
```
