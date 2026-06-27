# 🔧 ADR-008: Platform implementation of Euclidean division (v2)

> **Supersedes:** [ADR-003][ADR-003], whose description of the Native
> backend no longer matches the implementation since the rewrite recorded in
> [ADR-007][ADR-007].

This document records how Euclidean division semantics (decided in
[ADR-001][ADR-001]) are implemented across the three
`PlatformInteger` backends.

## 🤔 Context

`Integer` delegates all arithmetic to `PlatformInteger`, an `expect`/`actual`
interface with three implementations:

- `JvmInteger` — wraps `java.math.BigInteger`
- `JsInteger` — wraps JS `BigInt`
- `NativeInteger` — a custom sign-magnitude integer
  (`magnitude: UIntArray` + `sign: IntegerSign`), as decided in
  [ADR-007][ADR-007]

ADR-001 decided *what* the semantics should be. This ADR records *how* they
are achieved on each platform.

The core challenge is that none of the three backends natively computes the
Euclidean quotient and remainder as a single, sign-safe operation. JVM and JS
provide only T-division (`%` follows the sign of the dividend) or a `mod()`
function that may require a positive modulus. Native has no built-in
arbitrary-precision division at all — both `div` and `rem` are implemented from
scratch on top of the magnitude representation.

## ✅ Decision

The per-platform `rem`/`div` strategies are:

- **JVM:** `rem` is the primary operation —
  `dividend.mod(divisor.abs())`. Java's `BigInteger.mod(m)` requires `m > 0`
  and always returns a non-negative result. Passing the absolute value of the
  divisor handles negative divisors without additional branching. `div` is
  then derived from `rem` as `(dividend - remainder) / divisor`, so the
  Euclidean identity `a = q·b + r` holds by construction.

- **JS:** `rem` is the primary operation, computed as the T-remainder via
  `%`, then `|divisor|` is added when the result is negative. JS `BigInt`
  exposes only T-division; no native `mod` with a sign-safe contract exists.
  `div` is derived from `rem` the same way as on JVM.

- **Native:** `div` and `rem` are produced **together** by a single shared
  `divModMagnitudes` helper — neither is derived from the other. This helper
  performs binary long division on the absolute-value magnitudes, which
  yields a non-negative quotient/remainder pair by construction. If the
  dividend is negative, the result is corrected to satisfy Euclidean
  semantics: the quotient magnitude is incremented by one, and the remainder
  is replaced by `|divisor| − remainder`. This guarantees `0 ≤ r < |b|` for
  every combination of signs.

## 🔗 Consequences

- Cross-platform consistency: all three backends produce identical results
  for any `(a, b)` pair, despite using three distinct sign-correction
  strategies — JVM's `mod()` delegation, JS's `%`-then-add, and Native's
  magnitude-division-then-correct.
- On JVM and JS, `div` calling `rem` remains the load-bearing derivation
  order — `rem` must be defined first, and tests verifying Euclidean
  semantics should target `rem` directly. **This does not apply to Native**,
  where `div` and `rem` are two views of the same `divModMagnitudes` result;
  tests for Native should exercise both `div` and `rem` independently.
- Future platforms should pick whichever sign-correction pattern best fits
  their available primitives — a `mod`-with-positive-modulus operation
  (JVM-style), a `%`-then-add correction (JS-style), or a from-scratch
  magnitude algorithm with post-hoc correction (Native-style) — as long as
  the Euclidean identity (`a = q·b + r`, `0 ≤ r < |b|`) holds.

<!----------------------------------- Links ----------------------------------->

[ADR-001]: ADR-001-integer-euclidean-division.md
[ADR-003]: ADR-003-euclidean-division-platform-impl.md
[ADR-007]: ADR-007-native-integer-sign-magnitude-repr.md
