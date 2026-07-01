# 📣 Integer representation teaser — cross-posting

See `README.md`'s distribution strategy (steps 3-4) for where to post this
teaser, replacing the placeholder link below with the GitHub Discussion URL.

---

**⚡ Why Kotools Types' `Integer` no longer stores its value as a `String`**

Every arithmetic operation used to reparse a `String` from scratch and re-format
the result back into one — a cost that grows with every digit, on every call.
Kotools Types 5.2.0 replaces that with a real arbitrary-precision representation
per platform (`BigInteger` on JVM, `BigInt` on JS, a custom implementation on
Native), with parsing only happening at the actual boundaries.

We wrote a short post about what changed and why, one of the most frequently
requested changes from the community.

[Read it here]() 👇
