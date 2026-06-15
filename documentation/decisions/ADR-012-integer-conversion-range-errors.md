# 🏷️ ADR-012: Out-of-range error handling for `Integer` conversions

This document records how [`Integer`][Integer] reports out-of-range values
when converting to [`Byte`], [`Short`], [`Int`] or [`Long`].

## 🤔 Context

[`Integer`][Integer] (`org.kotools.types`) has arbitrary precision: its value
isn't bounded by [`Byte`], [`Short`], [`Int`] or [`Long`]'s ranges. Converting
an [`Integer`][Integer] to one of these fixed-size types
(`toByte`/`toShort`/`toInt`/`toLong`) can therefore fail when the
[`Integer`][Integer]'s value falls outside the target type's range.

The library already has two established patterns for this kind of failure:

- [`Integer.div`][Integer] and [`Integer.rem`][Integer] throw
  [`ArithmeticException`] for an invalid arithmetic operation (division by
  zero), each with a Kotlin-only `OrNull` counterpart
  ([`divOrNull`][Integer], [`remOrNull`][Integer]) that returns `null` instead
  of throwing.
- [ADR-010] and [ADR-011] define the `<description>: <value>` format for
  `org.kotools.types.*` error messages.

A consistent choice was needed for the new conversion functions.

## ✅ Decision

`toByte`, `toShort`, `toInt` and `toLong` throw an [`ArithmeticException`] when
this [`Integer`][Integer]'s value is outside the target type's range
(`<Type>.MIN_VALUE..<Type>.MAX_VALUE`). [`ArithmeticException`] is reused from
the [`div`][Integer]/[`rem`][Integer] convention above instead of introducing
a new exception type.

The exception message follows the [ADR-010] format, with this
[`Integer`][Integer] as the (unquoted, numeric) offending value:

```
Integer out of range for <Type>: <value>
```

Each function has a Kotlin-only `toByteOrNull`/`toShortOrNull`/`toIntOrNull`/
`toLongOrNull` counterpart that returns `null` instead of throwing, mirroring
[`divOrNull`][Integer]/[`remOrNull`][Integer]. Like these, the `OrNull`
variants are annotated `@JvmSynthetic` and hidden from Java, because
nullability isn't explicit in Java's type system.

## 🔗 Consequences

- [`Integer`][Integer] gains `toByte`/`toShort`/`toInt`/`toLong` and their
  `OrNull` counterparts, all following this shared range-check and error
  format.
- Any future `Integer` conversion that can fail due to a range constraint
  should follow this same `ArithmeticException` / `OrNull` pairing and the
  [ADR-010] message format.

<!----------------------------------- Links ----------------------------------->

[ADR-010]: ADR-010-error-message-format.md
[ADR-011]: ADR-011-error-message-quote-escaping.md
[Integer]: ../../subprojects/library/src/commonMain/kotlin/org/kotools/types/number/Integer.kt
[ArithmeticException]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-arithmetic-exception/
[Byte]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-byte/
[Short]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-short/
[Int]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/
[Long]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/
