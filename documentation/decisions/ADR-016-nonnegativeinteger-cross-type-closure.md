# ⚖️ ADR-015: Cross-type closure for `NonNegativeInteger` arithmetic

This document records the decision to expose `unaryMinus` and a
`times(NonPositiveInteger)` overload on the `NonNegativeInteger` type, both
returning a `NonPositiveInteger` instead of `NonNegativeInteger` or `Integer`.

## 🤔 Context

[ADR-014] excludes `minus` from `NonNegativeInteger` because the set of
non-negative integers is not closed under subtraction. The same reasoning raises
a related question for two other operations:

- Negating a non-negative integer (`-x`) never produces another non-negative
  integer (except for `0`); the set of non-negative integers isn't closed under
  negation either.
- Multiplying a non-negative integer by a `NonPositiveInteger` (e.g.
  `3 * (-1) = -3`) never produces a non-negative integer.

Unlike `minus`, however, both results are still fully constrained: negating a
non-negative integer always yields a value less than or equal to zero, and so
does multiplying it by a non-positive one. The set of non-negative integers
isn't closed under these operations, but the set of **non-positive** integers
is always reachable as the result.

## ✅ Decision: Expose closure under the refined target type

`NonNegativeInteger` exposes:

```kotlin
public operator fun unaryMinus(): NonPositiveInteger
public operator fun times(other: NonPositiveInteger): NonPositiveInteger
```

Both functions return `NonPositiveInteger` rather than `NonNegativeInteger`
(which would be incorrect) or the unconstrained `Integer` (which would discard
information that's actually guaranteed by the input types).

**Rationale:**

- **Closure holds under a different, but still refined, type.** Although
  `NonNegativeInteger` isn't closed under negation or under multiplication by
  a `NonPositiveInteger`, `NonPositiveInteger` is always the resulting type: the
  negative of a non-negative integer is always less than or equal to zero, and a
  non-negative integer times a non-positive one is always less than or equal to
  zero. Returning `NonPositiveInteger` keeps both functions total, consistent
  with the "operators are total functions over their declared types" principle
  from [ADR-013] and [ADR-014].
- **`0` resolves unambiguously.** `0` is both non-negative and non-positive.
  `-0 == 0` and `0 * y == 0` for any `NonPositiveInteger` `y`, and `0` is a
  valid `NonPositiveInteger` (`NonPositiveInteger.fromInteger` accepts values
  `<= 0`), so the boundary case resolves cleanly without any special-casing.
- **Widening to `Integer` would discard a guarantee callers can rely on.**
  Callers already know the sign of the result from the types involved; using
  `Integer` instead of `NonPositiveInteger` would force them to re-narrow the
  type themselves even though the information is already available statically.
- **This is distinct from the `minus` exclusion in [ADR-014].** `minus` is
  excluded because its result's sign depends on the relative magnitude of its
  operands (`1 - 2 = -1`, `2 - 1 = 1`): no single refined type can describe it.
  `unaryMinus` and `times(NonPositiveInteger)` have no such ambiguity — their
  result's sign is fixed by the operand types alone.

## 🔗 Consequences

- This establishes the precedent that cross-type closure (an operation on type
  `A` that isn't closed under `A`, but is closed under a related refined type
  `B`) should be exposed with `B` as the return type, rather than omitted or
  widened to `Integer`. Future symmetric additions should follow the same
  principle.
- The KDoc of `NonNegativeInteger` documents both functions alongside `plus` and
  `times(NonNegativeInteger)`, clarifying which operations stay within
  `NonNegativeInteger` and which cross over to `NonPositiveInteger`.

<!----------------------------------- Links ----------------------------------->

[ADR-013]: ADR-013-nonzerointeger-additive-exclusion.md
[ADR-014]: ADR-014-nonnegativeinteger-subtractive-exclusion.md
