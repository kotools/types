# ⚖️ ADR-002: Value semantics for `Integer` arithmetic operations

This document records the decision to eliminate reference-identity
short-circuits from [`Integer`][Integer] arithmetic operations.

## 🤔 Context

The initial implementation of `div` and `divOrNull` contained short-circuit
branches that returned `this` (the receiver) directly for two special cases:

- `dividend == Integer.of(0)` → return `this` (zero divided by anything is zero)
- `divisor == Integer.of(1)` → return `this` (dividing by one is identity)

The other arithmetic operations (`plus`, `minus`, `times`, `rem`) had no such
shortcuts and always returned freshly constructed values. This inconsistency
raised a question: what guarantee should `Integer` provide about the identity
of returned values?

## ✅ Decision: Arithmetic operations always return fresh values

All arithmetic operations on `Integer` must return a freshly constructed value.
No operation may return `this` or any input operand as its result.

**Rationale:**

- `Integer` is a value type: its identity is its value, not its object
  reference. Reference equality (`===`) should never be observable as a
  side effect of arithmetic.
- Structural equality (`==`) is the only meaningful form of equality for
  `Integer`. Callers must never be able to distinguish whether the result is
  the same object as an input.
- Returning `this` in `div`/`divOrNull` was inconsistent with how `plus`,
  `minus`, `times`, and `rem` already behaved — those always produced fresh
  values regardless of inputs.
- The short-circuits provided no meaningful performance benefit: the underlying
  platform `BigInteger` implementations handle trivial cases efficiently.

## 🔗 Consequences

- `x / Integer.of(1)` returns a value structurally equal to `x` but not the
  same object (`x !== result`).
- Tests asserting reference identity (`assertSame`) on arithmetic results are
  invalid by design and must not be written.
- Future arithmetic operations must follow the same rule: never return `this`
  or any input operand.

<!----------------------------------- Links ----------------------------------->

[Integer]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
