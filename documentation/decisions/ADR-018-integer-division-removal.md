# 🔥 ADR-018: Removal of `Integer`-accepting division from `Integer`

This document records the decision to remove the `div(Integer)`,
`divOrNull(Integer)`, `rem(Integer)` and `remOrNull(Integer)` overloads from
the `Integer` type's arithmetic API.

## 🤔 Context

Since [ADR-015], `Integer` division has had two overlapping APIs for the same
operation:

- The original partial one: `div(Integer)`/`rem(Integer)`, which throw an
  `ArithmeticException` on a zero divisor, and `divOrNull(Integer)`/
  `remOrNull(Integer)`, which return `null` instead.
- The newer total one: `div(NonZeroInteger)`/`rem(NonZeroInteger)`, which can
  never fail because their parameter type rules out zero by construction.

Keeping both means callers have two different failure stories for the same
arithmetic operation, depending on which overload they happen to call.

## ✅ Decision: Remove the `Integer`-accepting division overloads

`Integer` loses `div(Integer)`, `divOrNull(Integer)`, `rem(Integer)` and
`remOrNull(Integer)`. Callers needing a typed divisor now construct a
`NonZeroInteger` once (validated at the boundary) and reuse the total
`div(NonZeroInteger)`/`rem(NonZeroInteger)` overloads instead.

**Rationale:**

- **Rejecting zero at construction removes a runtime failure mode entirely.**
  `NonZeroInteger` rejects zero with an `IllegalArgumentException` at
  construction time, instead of `div`/`rem` throwing an `ArithmeticException`
  (or returning `null`) every time a division is performed. There is no
  longer a need to document or test that failure mode at all.
- **`Integer` is still experimental.** Per the
  [declarations lifecycle policy], experimental declarations can be removed
  directly, without a deprecation cycle.

## 🔗 Consequences

- Internal callers depending on the removed overloads (e.g.
  `Decimal.normalize()`) migrate to `NonZeroInteger`.
- The class-level KDoc "Division by zero" motivation section is rewritten to
  demonstrate the structural guarantee provided by `NonZeroInteger`, instead
  of a caught `ArithmeticException`.
- [ADR-015]'s total `div(NonZeroInteger)`/`rem(NonZeroInteger)` overloads
  become the only way to divide an `Integer` by a typed divisor.
- Future `Integer` arithmetic taking a divisor should default to
  `NonZeroInteger`, not `Integer`.

<!----------------------------------- Links ----------------------------------->

[ADR-015]: ADR-015-integer-typed-divisor-division.md
[declarations lifecycle policy]: ../declarations-lifecycle.md
