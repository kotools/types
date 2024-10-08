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

- The `Zero.Companion.orThrow(Byte)` **experimental** method for Kotlin and Java
  platforms, and the `Zero.Companion.orNull(Byte)` **experimental** method only
  for Kotlin platforms ([#688]).
- In the `EmailAddress.Companion` **experimental** type, the `orThrow(String)`
  and the `orThrow(String, String)` methods for Kotlin and Java platforms, the
  `orNull(String)` and the `orNull(String, String)` methods only for Kotlin
  platforms ([#692]).
- The `ZeroAsByteSerializer` **experimental** type, in the
  `types-kotlinx-serialization` subproject, for serializing the `Zero` type as
  `Byte` ([#690]).
- The `EmailAddressAsStringSerializer` **experimental** type, in the
  `types-kotlinx-serialization` subproject, for serializing the `EmailAddress`
  type as `String` ([#691]).

### 🗑️ Deprecated

- In the `Zero.Companion` **experimental** type, the `fromByte` and the
  `fromByteOrNull` methods with an **error level** for using the `orThrow` and
  the `orNull` ones instead ([#688]).
- In the `EmailAddress.Companion` **experimental** type, the `fromString` and
  the `fromStringOrNull` methods with an **error level** for using the `orThrow`
  and the `orNull` ones instead ([#692]).
- The `KotoolsTypesSerializers.zero` **experimental** property, from the
  `types-kotlinx-serialization` subproject, with an **error level** for using
  the `ZeroAsByteSerializer` type instead ([#690]).
- The `KotoolsTypesSerializers.emailAddress` **experimental** property, from the
  `types-kotlinx-serialization` subproject, with an **error level** for using
  the `EmailAddressAsStringSerializer` type instead ([#691]).

### 🔥 Removed

- Versions `4.0.0` and `4.3.0` from the API reference ([#704]).
- [JUnit 5] and [System Lambda] unused dependencies ([#703]).

### 🐛 Fixed

- Inappropriate usages of the `function` word instead of the `method` one in the
  API reference ([#706]).

### 🔒 Security

- Bumped Webpack from version `5.76.3` to `5.94.0` for fixing DOM Clobbering
  Gadget leading to XSS ([#728]).
- Bumped `rollup` NPM package from version `2.68.0` to `2.79.2` for fixing DOM
  Clobbering Gadget leading to XSS ([#730]).

---

Thanks to [@LVMVRQUXL] for contributing to this new release. 🙏

[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#688]: https://github.com/kotools/types/issues/688
[#690]: https://github.com/kotools/types/issues/690
[#691]: https://github.com/kotools/types/issues/691
[#692]: https://github.com/kotools/types/issues/692
[#703]: https://github.com/kotools/types/issues/703
[#704]: https://github.com/kotools/types/issues/704
[#706]: https://github.com/kotools/types/issues/706
[#728]: https://github.com/kotools/types/issues/728
[#730]: https://github.com/kotools/types/issues/730
[junit 5]: https://github.com/junit-team/junit5
[system lambda]: https://github.com/stefanbirkner/system-lambda

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
