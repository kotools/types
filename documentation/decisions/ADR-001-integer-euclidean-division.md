# ⚖️ ADR-001: Euclidean division for `Integer`

This document records two related architectural decisions made when implementing
division semantics for the `Integer` type.

## 🤔 Context

The `Integer` type models a mathematical integer (ℤ). When implementing `div`
and `rem` operators, two questions arose:

**Question 1 — Which division semantics?**

Three semantics exist for integer division when the operands have different
signs:

- **Truncating (T-division):** the remainder takes the sign of the dividend.
  This is the default on all Kotlin platforms (`Int`, `Long`).
  Example: `(-7).rem(2) == -1`
- **Floor division:** the remainder takes the sign of the divisor.
  Used in Python, Ruby, and Haskell's `mod`.
  Example: `(-7).rem(2) == 1`, but `7.rem(-2) == -1`
- **Euclidean:** the remainder is always non-negative (`0 ≤ r < |b|`), derived
  from the Euclidean division theorem.
  Example: `(-7).rem(2) == 1` and `7.rem(-2) == 1`

**Question 2 — Which API?**

A potential concern was that using `div` / `rem` operators for Euclidean
quotient and remainder could conflict with a future `Rational` type, where
`Integer / Integer` might be expected to return a `Rational` value.

## ✅ Decision 1: Euclidean semantics

`Integer.div` and `Integer.rem` follow **Euclidean division semantics**: the
remainder is always non-negative and strictly less than the absolute value of
the divisor (`0 ≤ r < |b|`).

**Rationale:**

- `Integer` models a mathematical concept, not a platform integer. Platform
  conventions (truncating) are implementation details, not mathematical laws.
- Euclidean division is uniquely defined: for any integers `a` and `b ≠ 0`,
  there exists exactly one pair `(q, r)` satisfying `a = q·b + r` and
  `0 ≤ r < |b|`. No other semantics offer this uniqueness guarantee.
- Floor division satisfies `0 ≤ r < |b|` only when `b > 0`, making it
  asymmetric. Euclidean is uniform regardless of the sign of the divisor.
- Truncating division produces surprising results for negative dividends — the
  exact behavior that `Integer` is designed to avoid.

## ✅ Decision 2: `div` and `rem` operators

The Euclidean quotient and remainder are exposed through the standard Kotlin
`div` (`/`) and `rem` (`%`) operators. No separate `quotient` / `modulo`
functions are introduced.

**Rationale:**

- Kotlin's type system does not allow overloading operators by return type. A
  future `Rational` type **cannot** reuse the same `div` operator to return a
  `Rational` — it would require a distinct API.
- Every language that provides both integer quotient and rational types uses
  separate construction syntax for rationals: Julia uses `//`, Haskell uses
  `Data.Ratio`, and Kotlin ecosystem libraries (e.g. `binkley/kotlin-rational`)
  use an `over` infix. A future `Rational` type in Kotools Types would follow
  this pattern (e.g. `Rational.of(a, b)` or `a over b`).
- Introducing dedicated functions (`quotient`, `modulo`) for the same concept
  would add unnecessary API surface and deviate from Kotlin conventions.

## 🔗 Consequences

- `Integer.of(-7) / Integer.of(2)` returns `-4` (truncating would return `-3`)
- `Integer.of(-7) % Integer.of(2)` returns `1` (truncating would return `-1`)
- `Integer.of(7) / Integer.of(-2)` returns `-3`, remainder `1`
- `Integer.of(-7) / Integer.of(-2)` returns `4`, remainder `1`
- The division algorithm `a = q·b + r` always holds with `0 ≤ r < |b|`
- A future `Rational` type must expose its construction through a dedicated API
  (factory function or infix operator) and must not reuse `div` / `rem`
