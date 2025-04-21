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

- The `EmailAddressRegex` **experimental** type representing a regular
  expression for validating email addresses, in the `types` Gradle subproject
  ([#809]).
- The `EmailAddress.Companion.orNull(String, EmailAddressRegex)` and the
  `EmailAddress.Companion.orThrow(String, EmailAddressRegex)` **experimental**
  functions, in the `types` Gradle subproject, for creating an email address
  matching the specified regular expression ([#809]).
- The `EmailAddressRegex.Companion.stringSerializer()` **experimental**
  function, in the `types-kotlinx-serialization` Gradle subproject, for
  serializing the `EmailAddressRegex` type as `String` ([#809]).
- The `KotoolsTypesSerializersModule()` **experimental** function, in the
  `types-kotlinx-serialization` Gradle subproject, for providing a collection of
  default serializers for serializing types provided by Kotools Types ([#790]).
- The following extension functions on the `Zero.Companion` **experimental**
  type, in the `types-kotlinx-serialization` Gradle subproject ([#793]):
  - `byteSerializer()` for serializing the `Zero` type as `Byte`.
  - `shortSerializer()` for serializing the `Zero` type as `Short`.
  - `intSerializer()` for serializing the `Zero` type as `Int`.
  - `longSerializer()` for serializing the `Zero` type as `Long`.
  - `floatSerializer()` for serializing the `Zero` type as `Float`.
  - `doubleSerializer()` for serializing the `Zero` type as `Double`.
- The `EmailAddress.Companion.stringSerializer()` **experimental** function, in
  the `types-kotlinx-serialization` Gradle subproject, for serializing the
  `EmailAddress` type as `String` ([#794]).
- Explanations associated to each symbol used in patterns present in the API
  reference ([fba9c4e4]).

### ♻️ Changed

- The README documentation of the `types-kotlinx-serialization` Gradle
  subproject, for using the new `KotoolsTypesSerializersModule()` function
  ([#790]).

### 🗑️ Deprecated

- The `EmailAddress.Companion.PATTERN` **experimental** property with an
  **error** level for using the `EmailAddressRegex.Companion.default()` function
  instead ([#809]).
- The `EmailAddress.Companion.orNull(String, String)` **experimental** function
  with an **error** level for using the `orNull(String, EmailAddressRegex)`
  overload instead ([#809]).
- The `EmailAddress.Companion.orThrow(String, String)` **experimental** function
  with an **error** level for using the `orThrow(String, EmailAddressRegex)`
  overload instead ([#809]).

### 🔥 Removed

- Versions 4.5.0, 4.5.1 and 4.5.2 from the API reference ([#771]).
- **Experimental** declarations from the `kotools.types.*` packages in the
  `types` Gradle subproject ([#802]). Better alternatives for these declarations
  will be introduced in the `org.kotools.types` package.
- The `KotoolsTypesSerializers` **experimental** type from the
  `types-kotlinx-serialization` Gradle subproject, for using the
  `KotoolsTypesSerializersModule()` function instead ([#790]).
- The `ZeroAsByteSerializer` **experimental** type, from the
  `types-kotlinx-serialization` Gradle subproject, for using the
  `Zero.Companion.byteSerializer()` function instead ([#795]).
- The `EmailAddressAsStringSerializer` **experimental** type, from the
  `types-kotlinx-serialization` Gradle subproject, for using the
  `EmailAddress.Companion.stringSerializer()` function instead ([#796]).

---

Thanks to [@LVMVRQUXL] for contributing to this new release. 🙏

[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#771]: https://github.com/kotools/types/issues/771
[#790]: https://github.com/kotools/types/issues/790
[#793]: https://github.com/kotools/types/issues/793
[#794]: https://github.com/kotools/types/issues/794
[#795]: https://github.com/kotools/types/issues/795
[#796]: https://github.com/kotools/types/issues/796
[#802]: https://github.com/kotools/types/issues/802
[#809]: https://github.com/kotools/types/issues/809
[fba9c4e4]: https://github.com/kotools/types/commit/fba9c4e4b9bfeef7a6239d7ed522f8140f97a92c

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
