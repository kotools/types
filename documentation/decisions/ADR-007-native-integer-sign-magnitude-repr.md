# ⚖️ ADR-007: Sign-magnitude representation for `NativeInteger`

This document records the decision about how
[`NativeInteger`][PlatformInteger.native] — the Kotlin/Native implementation
of [`PlatformInteger`][PlatformInteger] — represents and computes
arbitrary-precision integers.

## 🤔 Context

Until now, `NativeInteger` delegated all arithmetic to `BigInteger` from
`com.ionspin.kotlin:bignum`, the only third-party runtime dependency used
exclusively by the Kotlin/Native target.

One of the [design goals](../design-goals.md) of Kotools Types is to "avoid
useless dependencies" and depend only on what is strictly necessary. This
raised the question: can `NativeInteger` provide arbitrary-precision integer
arithmetic without relying on `kotlin-bignum`?

## ✅ Decision: Custom sign-magnitude representation

`NativeInteger` stores its value as a pair of
`(magnitude: UIntArray, sign: IntegerSign)`:

- `magnitude` holds the absolute value in **little-endian base-2³² limbs**,
  trimmed so that it never has a zero most-significant limb (the empty array
  represents zero).
- [`IntegerSign`][IntegerSign] is an internal enum (`Negative`, `Zero`,
  `Positive`) with `unaryMinus`, `times` (sign propagation for
  multiplication and division) and `compare` (for ordering).

A class invariant, enforced in `init`, ties the two fields together:
`magnitude` is empty **if and only if** `sign == Zero`, and `magnitude` never
carries a zero most-significant limb.

All operations — `plus`, `minus`, `times`, `div`, `rem`, `compareTo`,
`unaryMinus`, `parse`, and `toString` — are implemented from scratch on top
of magnitude-level algorithms (addition, subtraction, multiplication, binary
long division, bit shifts, and repeated division by ten for decimal
formatting).

**Rationale:**

- **Removes the only Native-only third-party dependency.** Aligns with the
  "avoid useless dependencies" design goal: `kotlin-bignum` is dropped from
  `gradle/libs.versions.toml` and
  `subprojects/internal/build.gradle.kts`.
- **Well-understood representation.** Sign-magnitude is the same
  representation used internally by `java.math.BigInteger` and most
  arbitrary-precision integer implementations, with well-known algorithms for
  every operation.
- **Canonical form enables cheap equality.** A trimmed magnitude (no leading
  zero limb, empty array for zero) gives each value a unique
  `(magnitude, sign)` representation, so `equals`/`hashCode` reduce to
  comparing the two fields directly — the same canonical-form principle
  established for `Decimal` in [ADR-005][ADR-005].
- **Self-documenting sign arithmetic.** Modeling the sign as an `IntegerSign`
  enum — rather than a raw `Int` (`-1`/`0`/`1`) — lets sign propagation
  (`times`, `unaryMinus`) and ordering (`compare`) be expressed as exhaustive
  `when` expressions checked by the compiler.

## 🔗 Consequences

- `nativeMainImplementation(libs.bignum)` and the corresponding `bignum`
  entries in `gradle/libs.versions.toml` are removed.
- The Native implementation of Euclidean division (`div`/`rem`) no longer
  delegates to a `mod()`-style function from `kotlin-bignum`; it is computed
  directly from the magnitude representation introduced here (see
  [ADR-008][ADR-008], which supersedes [ADR-003][ADR-003]).
- `IntegerSign` and the magnitude algorithms are private to `nativeMain` and
  have no impact on the common-API ABI dumps.
- Future bug fixes and performance improvements to Native arbitrary-precision
  arithmetic are made directly in
  `subprojects/internal/src/nativeMain/kotlin/org/kotools/types/internal/number/PlatformInteger.native.kt`,
  rather than tracked upstream in a third-party library.

<!----------------------------------- Links ----------------------------------->

[PlatformInteger]: ../../subprojects/internal/src/commonMain/kotlin/org/kotools/types/internal/number/PlatformInteger.kt
[PlatformInteger.native]: ../../subprojects/internal/src/nativeMain/kotlin/org/kotools/types/internal/number/PlatformInteger.native.kt
[IntegerSign]: ../../subprojects/internal/src/nativeMain/kotlin/org/kotools/types/internal/number/IntegerSign.kt
[ADR-003]: ADR-003-euclidean-division-platform-impl.md
[ADR-005]: ADR-005-decimal-canonical-form.md
[ADR-008]: ADR-008-euclidean-division-platform-impl-v2.md
