# ⚖️ ADR-006: Exclusion of division from `Decimal` arithmetic

This document records the decision to omit division (and modulo) from the
`Decimal` type's arithmetic API.

## 🤔 Context

`Decimal` provides arithmetic operations that keep results within the set of
terminating decimal numbers. Addition, subtraction, and multiplication all
belong to this category: the sum, difference, or product of two terminating
decimals is always a terminating decimal.

Division does not share this property. The question that arose during design
was: should `Decimal` provide a `div` operator anyway?

## ✅ Decision: Division is excluded

No `div` or `rem` operators are defined on `Decimal`. The only arithmetic
operations provided are unary minus (`-x`), addition (`x + y`), subtraction
(`x - y`), and multiplication (`x * y`).

**Rationale:**

- **The set of terminating decimals is not closed under division.** For any
  two terminating decimals `a` and `b ≠ 0`, `a / b` can produce a
  non-terminating result that `Decimal` cannot represent exactly
  (e.g., `1 / 3 = 0.333…`, `1 / 6 = 0.1666…`). Admitting division would
  therefore require either silent truncation or a different return type —
  neither of which is `Decimal`.
- **Silent truncation is incorrect.** Returning the nearest representable
  `Decimal` would make division lossy and non-invertible, violating the
  type's contract of exact arithmetic.
- **A different return type is a different abstraction.** Exact division of
  terminating decimals belongs to the rational numbers (ℚ), not to
  terminating decimals. Introducing such a result type is beyond the scope of
  `Decimal` and would belong to a future `Rational` type.
- **Consistent with `Integer` design.** The `Integer` type excludes
  operations that leave the set of integers: it has no square root and no
  non-integer division. `Decimal` follows the same principle — only
  operations that keep results within the modelled set are exposed.
- **Explicit delegation to the caller.** Users who need division can convert
  to `Double`, `Float`, or `java.math.BigDecimal` themselves, making the
  precision trade-off an explicit decision at the call site rather than a
  silent one inside `Decimal`.

## 🔗 Consequences

- The KDoc of `Decimal` includes a dedicated section ("Why no division?")
  that explains this design choice so users are not left guessing.
- The four provided operations (unary minus, `+`, `-`, `*`) form a ring over
  the set of terminating decimals — a complete and well-defined algebraic
  structure.
- A future `Rational` type (ℚ) could accept two `Decimal` (or `Integer`)
  values as its numerator and denominator, covering the exact-division use
  case without compromising `Decimal`.
