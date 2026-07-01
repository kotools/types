# 📣 Euclidean division teaser — cross-posting

See `README.md`'s distribution strategy (steps 3-4) for where to post this
teaser, replacing the placeholder link below with the GitHub Discussion URL.

---

**➗ Why `-7 % 2` should never be `-1`**

Kotlin's `%` truncates toward zero, so negative dividends produce negative
remainders — mathematically valid, but rarely what callers expect, and a common
source of off-by-one bugs on negative inputs. Kotools Types 5.2.0 makes
`Integer.div`/`rem` Euclidean: the remainder is always non-negative, and a typed
`NonZeroInteger` divisor rules out division by zero at compile time.

We wrote a short post about the three definitions of integer division and why
Euclidean is the one that matches "division with remainder" as taught in school.

[Read it here]() 👇
