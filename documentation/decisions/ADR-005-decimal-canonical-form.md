# ⚖️ ADR-005: Canonical form for `Decimal`

This document records the decision to enforce a unique canonical form for
every `Decimal` value, and how that invariant is maintained.

## 🤔 Context

The scaled-integer representation chosen in [ADR-004][ADR-004] allows the
same mathematical value to be expressed in multiple ways:

- `3.14` → `(314, 2)` and `3.140` → `(3140, 3)` represent the same number.
- `+42` and `42` and `042` all denote the same integer.
- `-0.0` and `0` both represent zero.

Without a normalization rule, two `Decimal` instances that compare as equal
under `compareTo` could have different `unscaledValue`/`scale` fields,
breaking structural equality and consistent hash codes.

## ✅ Decision: Enforce canonical form at every entry point

Every `Decimal` instance is stored in a unique **canonical form** defined by
the following rules:

1. No leading zeros in the integer part (the part before the decimal point).
2. No trailing zeros in the fractional part (the part after the decimal point).
3. No leading `+` sign.
4. Negative zero is normalized to positive zero.

As a consequence of rules 1 and 2, the `scale` field carries exactly the
number of significant fractional digits, and the `unscaledValue` field is
never divisible by 10 unless the fractional part is empty (scale = 0).

**Rationale:**

- **Trivial structural equality.** With canonical form guaranteed, `equals`
  reduces to comparing `unscaledValue` and `scale` as plain fields — no
  scale-alignment is needed during equality checks.
- **Correct hash codes.** `hashCode` is computed from `unscaledValue` and
  `scale`. Without canonical form, `Decimal.parse("3.14")` and
  `Decimal.parse("3.140")` would have different hash codes despite being
  equal, violating the `equals`/`hashCode` contract.
- **Deterministic `toString`.** The canonical form makes `toString` round-trip
  safely through `parse`: `Decimal.parse(x.toString()) == x` always holds.
- **Standard mathematical convention.** Trailing zeros in the fractional part
  carry no information; removing them follows standard mathematical notation.

**Implementation:** Canonical form is maintained at two entry points:

- **Parsing** (`parse` / `parseOrNull`): the private `String.toDecimal()`
  function strips leading zeros, trailing fractional zeros, and the leading
  `+` sign before constructing the `Decimal`. No post-construction
  normalization pass is needed.
- **Arithmetic operations** (`plus`, `minus`, `times`): the private
  `normalize()` function is called on the raw result. It repeatedly divides
  `unscaledValue` by 10 and decrements `scale` while `scale > 0` and
  `unscaledValue` is divisible by 10. Divisibility is tested using
  `Integer.rem(10)`, which follows Euclidean semantics (remainder always
  ≥ 0), ensuring correct detection for negative values
  (e.g., `−30 rem 10 = 0`).

## 🔗 Consequences

- `Decimal.parse("3.14") == Decimal.parse("+03.140")` is `true`.
- `Decimal.parse("3.14").hashCode() == Decimal.parse("3.140").hashCode()` is `true`.
- `Decimal.fromLong(0).toString()` returns `"0"` (not `"-0"` or `"0.0"`).
- `compareTo` cannot rely on canonical form alone to order values: two decimals
  with different scales still require scale-alignment of their unscaled values
  before the `Integer` comparison.
- `unaryMinus` does not call `normalize()` because negating a canonical value
  cannot introduce trailing fractional zeros.
- Any future arithmetic operation must call `normalize()` on its result before
  returning.

<!----------------------------------- Links ----------------------------------->

[ADR-004]: ADR-004-decimal-scaled-integer-repr.md
