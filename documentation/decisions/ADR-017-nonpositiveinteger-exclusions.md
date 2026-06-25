# ⚖️ ADR-017: Exclusions from `NonPositiveInteger` arithmetic

This document records the decision to omit certain subtraction and
multiplication combinations, as well as division and remainder, from the
`NonPositiveInteger` type's arithmetic API.

## 🤔 Context

`NonPositiveInteger` represents an `Integer` that is less than or equal to zero.
Negation (`unaryMinus`), addition (`plus`), subtraction (`minus`), and
multiplication (`times`) are provided wherever their result can always be
represented within `NonPositiveInteger` or `NonNegativeInteger`:

- `-x` is always non-negative.
- `x + y` (two non-positive integers) is always non-positive.
- `x - y` (a non-positive integer minus a non-negative one) is always
  non-positive.
- `x * y` (a non-positive integer times a non-negative one) is always
  non-positive.
- `x * y` (two non-positive integers) is always non-negative.

The question that arose during design was: should `NonPositiveInteger` also
provide `minus` overloads accepting `NonPositiveInteger`, `NonZeroInteger`, or
`Integer`; a `times` overload accepting `NonZeroInteger`; or `div`/`rem`
operators, like `Integer` does?

## ✅ Decision: Some combinations are excluded

Only the five operations listed above are defined on `NonPositiveInteger`. The
following are intentionally absent:

- **`minus(NonPositiveInteger)`, `minus(NonZeroInteger)`, and
  `minus(Integer)`.**
- **`times(NonZeroInteger)`.**
- **`div` and `rem`, for any operand type.**

**Rationale:**

- **The set of non-positive integers is not closed under subtracting another
  non-positive, non-zero, or unrestricted integer.** For any two non-positive
  integers `x` and `y` where `x > y`, `x - y` is positive (e.g.,
  `-1 - (-5) = 4`). The same unrestricted sign applies when subtracting a
  `NonZeroInteger` or an `Integer`. The result of these subtractions cannot
  always be represented as a `NonPositiveInteger`.
- **The set of non-positive integers is not closed under multiplying by a
  non-zero integer.** A `NonZeroInteger` can be positive or negative, so the
  sign of `x * y` is indeterminate: it cannot be guaranteed non-positive or
  non-negative, and therefore cannot be represented as either
  `NonPositiveInteger` or `NonNegativeInteger`.
- **A nullable, throwing, or widened return type would break the type's
  contract.** Returning an optional type, throwing on the unrepresentable case,
  or widening the return type to `Integer` would make these operators behave
  unlike every other arithmetic operator in this codebase, where operators are
  total functions over their declared types.
- **Consistent with `NonZeroInteger` and `NonNegativeInteger` design.**
  [ADR-013] and [ADR-014] exclude operations from those types for the same
  underlying reason: only operations that keep results within a modeled set are
  exposed. `NonPositiveInteger` follows the same principle.
- **`div` and `rem` are excluded entirely, consistent with every other
  variant.** None of `NonZeroInteger`, `NonNegativeInteger`, or
  `NonPositiveInteger` define division or remainder operators, because their
  semantics would be identical to `Integer.div` and `Integer.rem` (Euclidean
  division). Adding them to a variant would be redundant rather than
  closure-driven.
- **The five included operations stay because closure holds.** Each of
  `unaryMinus`, `plus`, the `minus(NonNegativeInteger)` overload, and both
  `times` overloads always produces a value representable in
  `NonPositiveInteger` or `NonNegativeInteger`, so they are safe to expose as
  total functions.
- **Explicit delegation to the caller.** Users who need an excluded operation
  can convert to `Integer` via `toInteger`, perform the operation there, and
  convert back with `fromInteger` (or its `OrNull` variant) if they need to
  re-establish the non-positive invariant, making the unrepresentable case an
  explicit decision at the call site rather than a silent one inside
  `NonPositiveInteger`.

## 🔗 Consequences

- The KDoc of `NonPositiveInteger` documents which combinations are included,
  with a counter-example illustrating why some are missing, so users are not
  left guessing why `NonPositiveInteger` doesn't mirror every arithmetic
  operator of `Integer`. This ADR, not the KDoc, is the contributor-facing
  record of the full exclusion rationale.
- The five provided operations are each closed over `NonPositiveInteger` or
  `NonNegativeInteger`.
- Converting to `Integer` remains the documented path for any arithmetic that
  isn't closed under "non-positive", consistent with how `NonZeroInteger` and
  `NonNegativeInteger` document converting to `Integer` for their own excluded
  operations.

<!----------------------------------- Links ----------------------------------->

[ADR-013]: ADR-013-nonzerointeger-additive-exclusion.md
[ADR-014]: ADR-014-nonnegativeinteger-subtractive-exclusion.md
