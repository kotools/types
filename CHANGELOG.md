# 🔄 Changelog

All notable changes to this project will be documented in this file.

> The format of this document is based on
> [Keep a Changelog](https://keepachangelog.com/en/1.1.0).

## 🤔 Types of changes

- `✨ Added` for new features.
- `♻️ Changed` for changes in existing functionality.
- `🗑️ Deprecated` for soon-to-be removed features.
- `🔥 Removed` for now removed features.
- `🐛 Fixed` for any bug fixes.
- `🔒 Security` in case of vulnerabilities.

## 🚧 Unreleased

### ✨ Added

- `Decimal` **experimental** type to `org.kotools.types.number` package,
  representing mathematical decimal numbers with exact arithmetic. Supports
  creation via `of(Long)`, `parse(String)` and `parseOrNull(String)`,
  arithmetic operators (`+`, `-`, `*`, unary `-`), comparison, and canonical
  string conversion. Division is intentionally excluded because the set of
  terminating decimals is not closed under division. ([#925])
- `Integer.toLong` and `Integer.toLongOrNull` **experimental** functions,
  returning the `Long` representation of an `Integer`, or throwing an
  `IllegalArgumentException` (`toLong`) or returning `null`
  (`toLongOrNull`) if it's out of range for `Long`. ([#1010])
- `NonZeroInteger` **experimental** type to `org.kotools.types.number` package,
  representing an `Integer` other than zero. Supports creation via
  `fromLong(Long)`, `fromInteger(Integer)`, `parse(String)` and their
  `OrNull` variants, structural equality, arithmetic operators (unary `-`
  and `*`), and conversions back to `Integer` and to its decimal string
  representation. Addition and subtraction are intentionally excluded
  because the set of non-zero integers is not closed under these operations.
  ([#1011])
- `NonNegativeInteger` **experimental** type to `org.kotools.types.number`
  package, representing an `Integer` that is greater than or equal to zero.
  Supports creation via `fromLong(Long)`, `fromInteger(Integer)`,
  `parse(String)` and their `OrNull` variants, structural equality,
  arithmetic operators (`+` and `*`), and conversions back to `Integer` and
  to its decimal string representation. Subtraction is intentionally
  excluded because the set of non-negative integers is not closed under this
  operation. ([#998])

### ♻️ Changed

- Changed internal representation of `Integer` **experimental** type from
  `String` to `java.lang.BigInteger` on Kotlin/JVM, `BigInt` on Kotlin/JS, and a
  custom implementation on Kotlin/Native. This avoids parsing when performing
  arithmetic operations. ([#912], [#914] reviewed by [@daniel-rusu]).
- Changed `Integer.div`, `Integer.divOrNull`, `Integer.rem` and
  `Integer.remOrNull` **experimental** functions to use Euclidean division
  semantics: the remainder is always non-negative (`0 ≤ r < |b|`). ([#952])
- Moved `Integer` **experimental** type to `org.kotools.types.number` package,
  and renamed some of its functions (`from` -> `fromLong`, `fromDecimal` ->
  `parse`, `fromDecimalOrNull` -> `parseOrNull`). ([#960], [#1010])
- Reformatted error messages thrown by **experimental** declarations in
  `org.kotools.types.*` (`Decimal`, `Integer`, `EmailAddressRegex`, and the
  `kotlinx-serialization` serializers) to follow a consistent
  `<description>: <value>` structure. ([#988])
- Optimized `parse` and `toString` in the Kotlin/Native implementation of
  `Integer` **experimental** type to process decimal digits in batches of 9
  (10⁹ per step) instead of one digit at a time, reducing iteration count
  ~9×. ([#989])

### 🔥 Removed

- `com.ionspin.kotlin:bignum` dependency, previously used internally by the
  Kotlin/Native implementation of the `Integer` **experimental** type. ([#962])
- `orNull` and `orThrow` **deprecated** functions from `EmailAddress.Companion`
  and `EmailAddressRegex.Companion` **experimental** types. Use their `of`
  functions instead. ([#871], [#872])

### 🐛 Fixed

- Fixed the `serialName` of the `SerialDescriptor` for the `EmailAddress` and
  `EmailAddressRegex` **experimental** default serializers provided by
  `KotoolsTypesSerializersModule()`. These now use explicit, fully-qualified
  names (`org.kotools.types.EmailAddress` and
  `org.kotools.types.EmailAddressRegex`) instead of the type's simple name.
  ([#966])
- Fixed default serializers provided by `KotoolsTypesSerializersModule()` to
  throw an `IllegalStateException` instead of an `IllegalArgumentException` when
  decoding an invalid value, aligning with the exception types recommended for
  `KSerializer` implementations. ([#990])

[@daniel-rusu]: https://github.com/daniel-rusu
[#871]: https://github.com/kotools/types/issues/871
[#872]: https://github.com/kotools/types/issues/872
[#912]: https://github.com/kotools/types/issues/912
[#914]: https://github.com/kotools/types/pull/914
[#925]: https://github.com/kotools/types/issues/925
[#952]: https://github.com/kotools/types/issues/952
[#960]: https://github.com/kotools/types/issues/960
[#962]: https://github.com/kotools/types/issues/962
[#966]: https://github.com/kotools/types/issues/966
[#988]: https://github.com/kotools/types/issues/988
[#989]: https://github.com/kotools/types/issues/989
[#990]: https://github.com/kotools/types/issues/990
[#998]: https://github.com/kotools/types/issues/998
[#1010]: https://github.com/kotools/types/issues/1010
[#1011]: https://github.com/kotools/types/issues/1011

## 🔖 Releases

| Version | Release date |
|---------|--------------|
| [5.1.1] | 2025-11-20   |
| [5.1.0] | 2025-11-17   |
| [5.0.1] | 2025-04-26   |
| [5.0.0] | 2025-01-13   |
| [4.5.3] | 2024-10-20   |
| [4.5.2] | 2024-07-24   |
| [4.5.1] | 2024-04-28   |
| [4.5.0] | 2024-03-14   |
| [4.4.2] | 2024-02-07   |
| [4.4.1] | 2024-02-02   |
| [4.4.0] | 2024-01-29   |
| [4.3.1] | 2023-09-25   |
| [4.3.0] | 2023-08-14   |
| [4.2.0] | 2023-06-24   |
| [4.1.0] | 2023-04-03   |
| [4.0.1] | 2023-02-06   |
| [4.0.0] | 2023-01-03   |
| [3.2.0] | 2022-12-13   |
| [3.1.1] | 2022-11-18   |
| [3.1.0] | 2022-10-24   |
| [3.0.1] | 2022-10-21   |
| [3.0.0] | 2022-10-16   |
| [2.0.0] | 2022-08-01   |
| [1.3.1] | 2022-08-01   |
| [1.3.0] | 2022-07-27   |
| [1.2.1] | 2022-08-01   |
| [1.2.0] | 2022-07-11   |
| [1.1.1] | 2022-08-01   |
| [1.1.0] | 2022-07-09   |
| [1.0.1] | 2022-03-21   |
| [1.0.0] | 2022-02-28   |

[5.1.1]: https://github.com/kotools/types/releases/tag/5.1.1
[5.1.0]: https://github.com/kotools/types/releases/tag/5.1.0
[5.0.1]: https://github.com/kotools/types/releases/tag/5.0.1
[5.0.0]: https://github.com/kotools/types/releases/tag/5.0.0
[4.5.3]: https://github.com/kotools/types/releases/tag/4.5.3
[4.5.2]: https://github.com/kotools/types/releases/tag/4.5.2
[4.5.1]: https://github.com/kotools/types/releases/tag/4.5.1
[4.5.0]: https://github.com/kotools/types/releases/tag/4.5.0
[4.4.2]: https://github.com/kotools/types/releases/tag/4.4.2
[4.4.1]: https://github.com/kotools/types/releases/tag/4.4.1
[4.4.0]: https://github.com/kotools/types/releases/tag/4.4.0
[4.3.1]: https://github.com/kotools/types/releases/tag/4.3.1
[4.3.0]: https://github.com/kotools/types/releases/tag/4.3.0
[4.2.0]: https://github.com/kotools/types/releases/tag/4.2.0
[4.1.0]: https://github.com/kotools/types/releases/tag/4.1.0
[4.0.1]: https://github.com/kotools/types/releases/tag/4.0.1
[4.0.0]: https://github.com/kotools/types/releases/tag/4.0.0
[3.2.0]: https://github.com/kotools/libraries/releases/tag/types-v3.2.0
[3.1.1]: https://github.com/kotools/libraries/releases/tag/types-v3.1.1
[3.1.0]: https://github.com/kotools/types-legacy/releases/tag/v3.1.0
[3.0.1]: https://github.com/kotools/types-legacy/releases/tag/v3.0.1
[3.0.0]: https://github.com/kotools/types-legacy/releases/tag/v3.0.0
[2.0.0]: https://github.com/kotools/types-legacy/releases/tag/v2.0.0
[1.3.1]: https://github.com/kotools/types-legacy/releases/tag/v1.3.1
[1.3.0]: https://github.com/kotools/types-legacy/releases/tag/v1.3.0
[1.2.1]: https://github.com/kotools/types-legacy/releases/tag/v1.2.1
[1.2.0]: https://github.com/kotools/types-legacy/releases/tag/v1.2.0
[1.1.1]: https://github.com/kotools/types-legacy/releases/tag/v1.1.1
[1.1.0]: https://github.com/kotools/types-legacy/releases/tag/v1.1.0
[1.0.1]: https://github.com/kotools/types-legacy/releases/tag/v1.0.1
[1.0.0]: https://github.com/kotools/types-legacy/releases/tag/v1.0.0
