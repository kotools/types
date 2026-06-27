# ⚖️ ADR-013: Exclusion of additive operations from `NonZeroInteger` arithmetic

This document records the decision to omit addition and subtraction from the
`NonZeroInteger` type's arithmetic API.

## 🤔 Context

`NonZeroInteger` represents an `Integer` that is other than zero.
Negation (`unaryMinus`) and multiplication (`times`) both keep results within
this set: the negation of a non-zero integer is never zero, and the product
of two non-zero integers is never zero either.

The question that arose during design was: should `NonZeroInteger` also
provide `plus` and `minus` operators, like `Integer` does?

## ✅ Decision: Addition and subtraction are excluded

No `plus` or `minus` operators are defined on `NonZeroInteger`. The only
arithmetic operations provided are unary minus (`-x`) and multiplication
(`x * y`).

**Rationale:**

- **The set of non-zero integers is not closed under addition or
  subtraction.** For any non-zero integer `x`, `x + (-x) = 0` and
  `x - x = 0`. Both operations can produce zero from two non-zero operands,
  so their result cannot always be represented as a `NonZeroInteger`.
- **A nullable, throwing, or widened return type would break the type's
  contract.** Returning `NonZeroInteger?` or throwing on the zero case would
  make `plus`/`minus` behave unlike every other arithmetic operator in this
  codebase, where operators are total functions over their declared types.
  Widening the return type to `Integer` would also be inconsistent: callers
  expecting closure under arithmetic operations would have to special-case
  `plus`/`minus` among the type's operators.
- **Consistent with `Decimal` design.** [ADR-006][ADR-006] excludes division
  from `Decimal` for the same underlying reason: the set of terminating
  decimals is not closed under division. `NonZeroInteger` follows the same
  principle — only operations that keep results within the modeled set are
  exposed.
- **`times` and `unaryMinus` stay because closure holds.** The product of two
  non-zero integers is always non-zero, and the negation of a non-zero
  integer is always non-zero, so both operations are safe to expose as total
  functions returning `NonZeroInteger`.
- **Explicit delegation to the caller.** Users who need addition or
  subtraction can convert to `Integer` via `toInteger`,
  perform the operation there, and convert back with
  `fromInteger` (or its `OrNull` variant) if they need to
  re-establish the non-zero invariant, making the zero case an explicit
  decision at the call site rather than a silent one inside
  `NonZeroInteger`.

## 🔗 Consequences

- The KDoc of `NonZeroInteger` documents this design choice so users are not
  left guessing why `+` and `-` are missing compared to `Integer`.
- The two provided operations (unary minus and `*`) are both closed over the
  set of non-zero integers.
- Converting to `Integer` remains the documented path for any arithmetic that
  isn't closed under "non-zero", consistent with how `Decimal` documents
  converting to `Double`/`BigDecimal` for division.

<!----------------------------------- Links ----------------------------------->

[ADR-006]: ADR-006-decimal-division-exclusion.md
