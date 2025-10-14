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

- The `PositiveInteger` **experimental** type, in the `org.kotools.types`
  package of the `types` module, for representing an integer that is greater
  than zero ([#661]). Contrarily to the integer types provided by Kotlin
  (`Byte`, `Short`, `Int` and `Long`), this type has no maximum value and can
  hold greater values than `Long.MAX_VALUE`.
- The `of(String)` and the `of(String, EmailAddressRegex)` **experimental**
  functions in the `EmailAddress.Companion` type, for creating an instance of
  `EmailAddress` from the specified `text` ([#865]).
- The `EmailAddressRegex.Companion.of(String)` **experimental** function, for
  creating an instance of `EmailAddressRegex` from the specified `pattern`
  ([#868]).

### ♻️ Changed

- The [declarations lifecycle](documentation/declarations-lifecycle.md)
  documentation ([1f9e8151a]).
- The samples of the `EmailAddress.Companion.orThrow(String)` function are now
  easier to understand ([0e1470a48]).
- The documentation of the `EmailAddress.Companion.orNull(String)` and the
  `EmailAddress.Companion.orThrow(String)` **experimental** functions refer to
  the `EmailAddressRegex.Companion.default()` **experimental** function instead
  of the `EmailAddress.Companion.PATTERN` **deprecated** property ([#838]).

### 🗑️ Deprecated

- The `orNull` and the `orThrow` functions of the `EmailAddress.Companion`
  **experimental** type, for using the `EmailAddress.Companion.of` functions
  instead ([#865]).
- The `orNull(String)` and the `orThrow(String)` functions of the
  `EmailAddressRegex.Companion` **experimental** type, for using the
  `EmailAddressRegex.Companion.of(String)` function instead ([#868]).

### 🔥 Removed

- The `PATTERN` **deprecated** property, the `orNull(String, String)` and the
  `orThrow(String, String)` **deprecated** functions from the
  `EmailAddress.Companion` **experimental** type ([#838]).
- The `Zero` **experimental** type and all its serializers ([#844]). Use
  directly the `0` value instead.

### 🐛 Fixed

- Run configurations in [contributing guidelines](CONTRIBUTING.md) ([#854]).

---

Thanks to [@LVMVRQUXL] for contributing to this new release. 🙏

[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#661]: https://github.com/kotools/types/issues/661
[#838]: https://github.com/kotools/types/issues/838
[#844]: https://github.com/kotools/types/issues/844
[#854]: https://github.com/kotools/types/issues/854
[#865]: https://github.com/kotools/types/issues/865
[#868]: https://github.com/kotools/types/issues/868
[0e1470a48]: https://github.com/kotools/types/commit/0e1470a48
[1f9e8151a]: https://github.com/kotools/types/commit/1f9e8151a

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
