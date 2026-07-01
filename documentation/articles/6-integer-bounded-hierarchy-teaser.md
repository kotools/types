# 📣 Bounded hierarchy teaser — cross-posting

See `README.md`'s distribution strategy (steps 3-4) for where to post this
teaser, replacing the placeholder link below with the GitHub Discussion URL.

---

**🔒 Making "this integer can never be negative" a type, not a check**

`require(count >= 0)` sprinkled at every call site is easy to forget and
impossible for the compiler to verify. Kotools Types 5.2.0 adds
`NonZeroInteger`, `NonNegativeInteger`, and `NonPositiveInteger` — sign
constraints baked into the type itself, with arithmetic operators that preserve
those guarantees across additions and multiplications.

We wrote a short post about the new hierarchy and how its closure-preserving
arithmetic works.

[Read it here]() 👇
