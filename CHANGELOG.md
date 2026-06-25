# 🔄 Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog], and this project adheres to its own
[versioning strategy].

## 🤔 Types of changes

- `✨ Added` for new features.
- `♻️ Changed` for changes in existing functionality.
- `🗑️ Deprecated` for soon-to-be removed features.
- `🔥 Removed` for now removed features.
- `🐛 Fixed` for bug fixes.
- `🔒 Security` for vulnerabilities.

## 🚧 Unreleased

### ✨ Added

- `toLong()` and `toLongOrNull()` **experimental** functions to `Integer` class,
  returning the `Long` representation of an `Integer`. (module: `types`, ref:
  [#1010])
- `div(NonZeroInteger)` and `rem(NonZeroInteger)` **experimental** functions to
  `Integer` class, returning the Euclidean quotient and non-negative remainder
  of dividing by a non-zero divisor without throwing. (module: `types`, ref:
  [#999])
- `NonZeroInteger` **experimental** class to `org.kotools.types.number` package,
  representing an `Integer` other than zero. (module: `types`, ref: [#1011])
- `NonNegativeInteger` **experimental** class to `org.kotools.types.number`
  package, representing an `Integer` that is greater than or equal to zero.
  (module: `types`, refs: [#998], [#1014])
- `NonPositiveInteger` **experimental** class to `org.kotools.types.number`
  package, representing an `Integer` that is less than or equal to zero,
  with arithmetic operations for negation, addition, subtraction, and
  multiplication. (module: `types`, refs: [#1013], [#1024])
- `Decimal` **experimental** class to `org.kotools.types.number` package,
  representing mathematical decimal numbers with exact arithmetic. (module:
  `types`, ref: [#925] originally suggested by [@CLOVIS-AI])

### ♻️ Changed

- Reformatted error messages thrown by **experimental** declarations in
  `org.kotools.types.*` to follow a consistent `<description>: <value>`
  structure. (modules: `types`, `types-kotlinx-serialization`, ref: [#988])
- **Breaking:** Moved `Integer` **experimental** class to
  `org.kotools.types.number` package, and renamed some of its functions
  (`from` -> `fromLong`, `fromDecimal` -> `parse`, `fromDecimalOrNull` ->
  `parseOrNull`). (module: `types`, refs: [#960], [#1010])
- `div`, `divOrNull`, `rem` and `remOrNull` **experimental** functions of
  `Integer` class to use Euclidean division semantics: the remainder is always
  non-negative (`0 <= r < |b|`). (module: `types`, ref: [#952])
- Internal representation of `Integer` **experimental** class from `String` to
  `java.lang.BigInteger` on Kotlin/JVM, `BigInt` on Kotlin/JS, and a custom
  implementation on Kotlin/Native. This avoids parsing when performing
  arithmetic operations. (module: `types`, refs: [#912], [#914] reviewed by
  [@daniel-rusu], [#989])

### 🔥 Removed

- **Breaking:** `orNull` and `orThrow` **deprecated** functions from
  `EmailAddress` and `EmailAddressRegex` **experimental** classes. Use their
  `of` function instead. (module: `types`, refs: [#871], [#872])
- `com.ionspin.kotlin:bignum` dependency, previously used internally by
  `Integer` on Kotlin/Native. (module: `types`, ref: [#962])

### 🐛 Fixed

- Serial name of default serializers, provided by
  `KotoolsTypesSerializersModule()` **experimental** function, now uses
  fully-qualified names (e.g., `org.kotools.types.EmailAddress`) instead of the
  type's simple name. (module: `types-kotlinx-serialization`, ref: [#966])
- Default serializers provided by `KotoolsTypesSerializersModule()`
  **experimental** function now throw an `IllegalStateException` instead of an
  `IllegalArgumentException` when decoding an invalid value. (module:
  `types-kotlinx-serialization`, ref: [#990])

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

<!----------------------------------- LINKS ----------------------------------->

[1.0.0]: https://github.com/kotools/types-legacy/releases/tag/v1.0.0
[1.0.1]: https://github.com/kotools/types-legacy/releases/tag/v1.0.1
[1.1.0]: https://github.com/kotools/types-legacy/releases/tag/v1.1.0
[1.1.1]: https://github.com/kotools/types-legacy/releases/tag/v1.1.1
[1.2.0]: https://github.com/kotools/types-legacy/releases/tag/v1.2.0
[1.2.1]: https://github.com/kotools/types-legacy/releases/tag/v1.2.1
[1.3.0]: https://github.com/kotools/types-legacy/releases/tag/v1.3.0
[1.3.1]: https://github.com/kotools/types-legacy/releases/tag/v1.3.1
[2.0.0]: https://github.com/kotools/types-legacy/releases/tag/v2.0.0
[3.0.0]: https://github.com/kotools/types-legacy/releases/tag/v3.0.0
[3.0.1]: https://github.com/kotools/types-legacy/releases/tag/v3.0.1
[3.1.0]: https://github.com/kotools/types-legacy/releases/tag/v3.1.0
[3.1.1]: https://github.com/kotools/libraries/releases/tag/types-v3.1.1
[3.2.0]: https://github.com/kotools/libraries/releases/tag/types-v3.2.0
[4.0.0]: https://github.com/kotools/types/releases/tag/4.0.0
[4.0.1]: https://github.com/kotools/types/releases/tag/4.0.1
[4.1.0]: https://github.com/kotools/types/releases/tag/4.1.0
[4.2.0]: https://github.com/kotools/types/releases/tag/4.2.0
[4.3.0]: https://github.com/kotools/types/releases/tag/4.3.0
[4.3.1]: https://github.com/kotools/types/releases/tag/4.3.1
[4.4.0]: https://github.com/kotools/types/releases/tag/4.4.0
[4.4.1]: https://github.com/kotools/types/releases/tag/4.4.1
[4.4.2]: https://github.com/kotools/types/releases/tag/4.4.2
[4.5.0]: https://github.com/kotools/types/releases/tag/4.5.0
[4.5.1]: https://github.com/kotools/types/releases/tag/4.5.1
[4.5.2]: https://github.com/kotools/types/releases/tag/4.5.2
[4.5.3]: https://github.com/kotools/types/releases/tag/4.5.3
[5.0.0]: https://github.com/kotools/types/releases/tag/5.0.0
[5.0.1]: https://github.com/kotools/types/releases/tag/5.0.1
[5.1.0]: https://github.com/kotools/types/releases/tag/5.1.0
[5.1.1]: https://github.com/kotools/types/releases/tag/5.1.1

[keep a changelog]: https://keepachangelog.com/en/2.0.0
[versioning strategy]: documentation/versioning-strategy.md

[@CLOVIS-AI]: https://github.com/CLOVIS-AI
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
[#999]: https://github.com/kotools/types/issues/999
[#1010]: https://github.com/kotools/types/issues/1010
[#1011]: https://github.com/kotools/types/issues/1011
[#1013]: https://github.com/kotools/types/issues/1013
[#1014]: https://github.com/kotools/types/issues/1014
[#1024]: https://github.com/kotools/types/issues/1024
