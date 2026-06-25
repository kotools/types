# ⚖️ ADR-015: Typed-divisor Euclidean division for `Integer`

This document records the decision to add `div(NonZeroInteger)` and
`rem(NonZeroInteger)` overloads to the [`Integer`][Integer] type's arithmetic
API.

## 🤔 Context

[`Integer.div(Integer)`][div] and [`Integer.rem(Integer)`][rem] implement
Euclidean division (see [ADR-001][ADR-001]), but must throw an
`ArithmeticException` when the divisor is zero, because nothing in the
`Integer` type rules out that case. The non-throwing `divOrNull`/`remOrNull`
variants exist for callers who'd rather get `null` than catch an exception.

Now that [`NonZeroInteger`][NonZeroInteger] exists, representing an `Integer`
that is guaranteed to never be zero, the zero-divisor case can be ruled out by
the type system instead of being checked at runtime. The question that arose
was: should `Integer` provide `div`/`rem` overloads accepting a
`NonZeroInteger` divisor?

## ✅ Decision: Add total `div`/`rem` overloads accepting `NonZeroInteger`

`Integer` gains two new overloads:

- `div(other: NonZeroInteger): Integer`
- `rem(other: NonZeroInteger): NonNegativeInteger`

**Rationale:**

- **A `NonZeroInteger` divisor makes these total functions.** Since the
  divisor can never be zero, there is no failure case left to handle: no
  exception to throw, and no `null` to return. Consequently, no
  `divOrNull(NonZeroInteger)` or `remOrNull(NonZeroInteger)` overloads are
  added — they would be redundant given there's nothing to make nullable.
- **`rem(NonZeroInteger)` returns [`NonNegativeInteger`][NonNegativeInteger],
  not `Integer`.** Euclidean division guarantees the remainder satisfies
  `0 <= r < |divisor|` (see [ADR-001][ADR-001]). Narrowing the return type to
  `NonNegativeInteger` encodes that postcondition directly in the type, instead
  of leaving callers to re-validate a non-negative `Integer` themselves.
- **Consistent with the codebase's typed-narrowing pattern.** This mirrors how
  [`NonZeroInteger.times`][NonZeroInteger] and
  [`NonNegativeInteger.times`][NonNegativeInteger] stay closed-form: narrowing
  an operand's type to rule out a failure case, and narrowing the return type
  to encode a postcondition, rather than introducing a new design language.

## 🔗 Consequences

- `div(NonZeroInteger)` and `rem(NonZeroInteger)` never throw and never return
  `null`; their KDoc documents this explicitly so callers don't expect the
  same failure modes as their `Integer`-parameter counterparts.
- Future typed-divisor overloads (if any) should follow the same shape:
  narrow the parameter to rule out the failure case, and narrow the return
  type to encode the postcondition.

<!----------------------------------- Links ----------------------------------->

[Integer]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
[div]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
[rem]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
[NonZeroInteger]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/NonZeroInteger.kt
[NonNegativeInteger]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/NonNegativeInteger.kt
[ADR-001]: ADR-001-integer-euclidean-division.md
