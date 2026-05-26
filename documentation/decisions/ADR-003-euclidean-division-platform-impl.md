# 🔧 ADR-003: Platform implementation of Euclidean division

This document records how Euclidean division semantics (decided in
[ADR-001][ADR-001]) are implemented across the three
[`PlatformInteger`][PlatformInteger] backends.

## 🤔 Context

`Integer` delegates all arithmetic to `PlatformInteger`, an `expect`/`actual`
interface with three implementations:

- [`JvmInteger`][PlatformInteger.jvm] — wraps `java.math.BigInteger`
- [`JsInteger`][PlatformInteger.js] — wraps JS `BigInt`
- [`NativeInteger`][PlatformInteger.native] — wraps kotlin-bignum `BigInteger`

ADR-001 decided *what* the semantics should be. This ADR records *how* they
are achieved on each platform.

The core challenge is that none of the three backends natively computes the
Euclidean quotient and remainder as a single, sign-safe operation. Each backend
provides only T-division (`%` follows the sign of the dividend) or a `mod()`
function that may require a positive modulus.

## ✅ Decision

`rem` is the primary operation on every platform; `div` is always derived from
it. The per-platform `rem` strategies are:

- **JVM:** `dividend.mod(divisor.abs())` — Java's `BigInteger.mod(m)` requires
  `m > 0` and always returns a non-negative result. Passing the absolute value
  of the divisor handles negative divisors without additional branching.

- **Native:** `dividend.mod(divisor.abs())` — kotlin-bignum's
  `BigInteger.mod(m)` has the same contract as Java's: non-negative result for
  a positive modulus. The same pattern as JVM applies.

- **JS:** T-remainder via `%`, then add `|divisor|` when the result is
  negative. JS `BigInt` exposes only T-division; no native `mod` with a
  sign-safe contract exists.

On every platform, `div` is then computed as `(dividend - remainder) / divisor`,
rather than independently, so the Euclidean identity `a = q·b + r` holds by
construction.

## 🔗 Consequences

- Cross-platform consistency: all three backends produce identical results for
  any `(a, b)` pair.
- Future platforms must use the same sign-correction pattern as JS unless a
  native `mod`-with-positive-modulus operation is available.
- `rem` is the load-bearing operation; `div` is secondary (derived from it).
  Tests verifying Euclidean semantics should target `rem` directly.
- The derivation order within each file is load-bearing: `div` calls `rem`, so
  `rem` must be defined first.

<!----------------------------------- Links ----------------------------------->

[ADR-001]: ADR-001-integer-euclidean-division.md
[PlatformInteger]: ../../subprojects/internal/src/commonMain/kotlin/org/kotools/types/internal/number/PlatformInteger.kt
[PlatformInteger.jvm]: ../../subprojects/internal/src/jvmMain/kotlin/org/kotools/types/internal/number/PlatformInteger.jvm.kt
[PlatformInteger.js]: ../../subprojects/internal/src/jsMain/kotlin/org/kotools/types/internal/number/PlatformInteger.js.kt
[PlatformInteger.native]: ../../subprojects/internal/src/nativeMain/kotlin/org/kotools/types/internal/number/PlatformInteger.native.kt
