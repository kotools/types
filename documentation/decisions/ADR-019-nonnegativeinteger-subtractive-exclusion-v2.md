# ⚖️ ADR-019: Exclusion of subtraction from `NonNegativeInteger` arithmetic (v2)

> **Supersedes:** [ADR-014], whose decision excluded all subtraction from
> `NonNegativeInteger`. This record narrows that exclusion to only
> `minus(NonNegativeInteger)`.

This document records the decision to add a `minus(NonPositiveInteger)`
operator to the `NonNegativeInteger` type's arithmetic API, while keeping
`minus(NonNegativeInteger)` excluded.

## 🤔 Context

[ADR-014] excluded all subtraction from `NonNegativeInteger`, reasoning that
the set of non-negative integers isn't closed under subtracting another
non-negative integer. [ADR-016] later extended `NonNegativeInteger`'s
arithmetic for cross-type closure (`unaryMinus`, `times(NonPositiveInteger)`),
without revisiting `minus`.

The question that arose was: should `NonNegativeInteger` also provide a
`minus` overload accepting a `NonPositiveInteger`, mirroring
`NonPositiveInteger.minus(NonNegativeInteger)` from [ADR-017]?

## ✅ Decision: `minus(NonPositiveInteger)` is included

A `minus` operator accepting a `NonPositiveInteger` is added to
`NonNegativeInteger`. `minus(NonNegativeInteger)` remains excluded.

**Rationale:**

- **The set of non-negative integers is closed under subtracting a
  non-positive integer.** For `x: NonNegativeInteger` and
  `y: NonPositiveInteger`, `x - y == x + (-y)`, and since `-y >= 0`, the
  result is always `>= x >= 0`. The result is always representable as a
  `NonNegativeInteger`.
- **Mirrors the symmetric case on `NonPositiveInteger`.** [ADR-017] includes
  `minus(NonNegativeInteger)` on `NonPositiveInteger` for the same closure
  reason. This decision applies the same principle in the other direction.
- **`minus(NonNegativeInteger)` remains excluded.** [ADR-014]'s original
  rationale still holds for this overload: for any two non-negative integers
  `x` and `y` where `x < y`, `x - y` is negative (e.g., `1 - 2 = -1`), so the
  result cannot always be represented as a `NonNegativeInteger`.
- **A nullable, throwing, or widened return type would break the type's
  contract.** As in [ADR-014] and [ADR-013], operators in this codebase are
  total functions over their declared types, so `minus(NonNegativeInteger)`
  is omitted entirely rather than weakened.

## 🔗 Consequences

- The KDoc of `NonNegativeInteger` is updated to document `minus` alongside
  `plus` and `times`, and to clarify that only `minus(NonNegativeInteger)`
  remains absent.
- [ADR-014] is marked as superseded by this record in the decisions index.
- `NonNegativeInteger`'s arithmetic set is now `plus`, `times`,
  `times(NonPositiveInteger)`, `unaryMinus` ([ADR-016]), and
  `minus(NonPositiveInteger)`.
- Converting to `Integer` remains the documented path for
  `minus(NonNegativeInteger)`, consistent with how `NonZeroInteger` and
  `NonPositiveInteger` document converting to `Integer` for their own
  excluded operations.

<!----------------------------------- Links ----------------------------------->

[ADR-013]: ADR-013-nonzerointeger-additive-exclusion.md
[ADR-014]: ADR-014-nonnegativeinteger-subtractive-exclusion.md
[ADR-016]: ADR-016-nonnegativeinteger-cross-type-closure.md
[ADR-017]: ADR-017-nonpositiveinteger-exclusions.md
