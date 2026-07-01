# 📣 Decimal teaser — cross-posting

See `README.md`'s distribution strategy (steps 3-4) for where to post this
teaser, replacing the placeholder link below with the GitHub Discussion URL.

---

**🎯 `0.1 + 0.2 != 0.3` — how Kotools Types' new `Decimal` type avoids it by
never using floating-point**

`Decimal`, new in Kotools Types 5.2.0, represents numbers as a scaled integer
instead of binary floating-point, making `+`, `-`, and `*` exact. There's
deliberately no `div`/`rem` — dividing two terminating decimals can produce a
number with no finite decimal representation, so `Decimal` won't silently round
it for you.

We wrote a short post about the representation, why it's exact, and why division
isn't part of the API.

[Read it here]() 👇
