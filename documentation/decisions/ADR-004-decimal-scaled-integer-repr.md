# ⚖️ ADR-004: Scaled-integer representation for `Decimal`

This document records the decision about how `Decimal` stores decimal numbers
internally.

## 🤔 Context

`Decimal` models a mathematical terminating decimal number (ℚ∩ℝ_decimal).
Three candidate representations were considered:

- **Floating-point (`Double` / `Float`):** the primitive types already
  available in Kotlin, with hardware-level arithmetic support on every platform.
- **Platform `BigDecimal`:** `java.math.BigDecimal` offers arbitrary-precision
  decimal arithmetic with sign, unscaled integer, and scale fields.
- **Scaled `Integer` pair:** represent the value as
  `(unscaledValue: Integer, scale: Int)`, where
  `value = unscaledValue × 10⁻ˢᶜᵃˡᵉ` (e.g., 3.14 → unscaled = 314, scale = 2).

## ✅ Decision: Scaled `Integer` pair

`Decimal` stores its value as a pair of `(unscaledValue: Integer, scale: Int)`,
with the invariant that `scale >= 0`. The `unscaledValue` holds the significant
digits of the decimal (including sign), and `scale` holds the number of
fractional digits.

**Rationale:**

- **Floating-point is inexact.** Binary IEEE 754 cannot represent most
  base-10 decimal values exactly. Operations such as `0.1 + 0.2` produce a
  result that differs from `0.3` at the binary level, leading to subtle
  rounding errors that `Decimal` is designed to avoid.
- **`BigDecimal` is JVM-only.** `java.math.BigDecimal` is not available on
  Kotlin/JS or Kotlin/Native. Using it as the backing representation would
  require platform-specific `expect`/`actual` bridges and introduce a heavy
  dependency on the JS and Native targets.
- **Reuses the library's own `Integer` type.** By delegating to `Integer`
  for the unscaled value, `Decimal` inherits arbitrary-precision integer
  arithmetic for free, avoids reinventing overflow-safe arithmetic, and
  remains consistent with the rest of the Kotools Types library.
- **Self-contained, cross-platform arithmetic.** All operations (+, -, ×,
  and scale alignment) reduce to `Integer` arithmetic, which already has
  cross-platform implementations. No external dependency is needed.

## 🔗 Consequences

- `Decimal` carries two fields (`unscaledValue`, `scale`), both private.
  No public accessor exposes the internal structure.
- Arithmetic operations (+, -, ×) are implemented in terms of scale
  alignment followed by `Integer` arithmetic (see [ADR-005][ADR-005] for
  the normalization step that follows).
- Ordering (`compareTo`) aligns both operands to the same scale before
  comparing their unscaled `Integer` values.
- The `toString()` output is derived directly from `unscaledValue` and
  `scale`, inserting a decimal point at the correct position and padding with
  leading zeros when the scale exceeds the number of significant digits
  (e.g., unscaled = 5, scale = 3 → `"0.005"`).

<!----------------------------------- Links ----------------------------------->

[ADR-005]: ADR-005-decimal-canonical-form.md
