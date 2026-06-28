# ⚖️ ADR-014: Exclusion of subtraction from `NonNegativeInteger` arithmetic

This document records the decision to omit subtraction from the
`NonNegativeInteger` type's arithmetic API.

## 🤔 Context

`NonNegativeInteger` represents an `Integer` that is greater than or equal to
zero. Addition (`plus`) and multiplication (`times`) both keep results within
this set: the sum and the product of two non-negative integers are always
non-negative.

The question that arose during design was: should `NonNegativeInteger` also
provide a `minus` operator, like `Integer` does?

## ✅ Decision: Subtraction is excluded

No `minus` operator is defined on `NonNegativeInteger`. The only arithmetic
operations provided are addition (`x + y`) and multiplication (`x * y`).

**Rationale:**

- **The set of non-negative integers is not closed under subtraction.** For any
  two non-negative integers `x` and `y` where `x < y`, `x - y` is negative
  (e.g., `1 - 2 = -1`). The result of subtraction cannot always be represented
  as a `NonNegativeInteger`.
- **A nullable, throwing, or widened return type would break the type's
  contract.** Returning `NonNegativeInteger?` or throwing on the negative case
  would make `minus` behave unlike every other arithmetic operator in this
  codebase, where operators are total functions over their declared types.
  Widening the return type to `Integer` would also be inconsistent: callers
  expecting closure under arithmetic operations would have to special-case
  `minus` among the type's operators.
- **Consistent with `NonZeroInteger` design.** [ADR-013] excludes addition and
  subtraction from `NonZeroInteger` for the same underlying reason: the set of
  non-zero integers is not closed under these operations. `NonNegativeInteger`
  follows the same principle — only operations that keep results within the
  modeled set are exposed.
- **`plus` and `times` stay because closure holds.** The sum and the product of
  two non-negative integers are always non-negative, so both operations are safe
  to expose as total functions returning `NonNegativeInteger`.
- **Explicit delegation to the caller.** Users who need subtraction can convert
  to `Integer` via `toInteger`, perform the operation there, and convert back
  with `fromInteger` (or its `OrNull` variant) if they need to re-establish the
  non-negative invariant, making the negative case an explicit decision at the
  call site rather than a silent one inside `NonNegativeInteger`.

## 🔗 Consequences

- The KDoc of `NonNegativeInteger` documents this design choice so users are not
  left guessing why `-` is missing compared to `Integer`.
- The two provided operations (`+` and `*`) are both closed over the set of
  non-negative integers.
- Converting to `Integer` remains the documented path for any arithmetic that
  isn't closed under "non-negative", consistent with how `NonZeroInteger`
  documents converting to `Integer` for addition and subtraction.

<!----------------------------------- Links ----------------------------------->

[ADR-013]: ADR-013-nonzerointeger-additive-exclusion.md
