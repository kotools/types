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

- `Integer.toSignedString()` function for returning the signed decimal
  representation of an integer
  ([#922](https://github.com/kotools/types/pull/922)).

### ♻️ Changed

- Improve the vision of Kotools Types in design goals, README and `Integer`
  type's documentation ([#913](https://github.com/kotools/types/pull/913)).
- Convert `Integer` **experimental** type from class to interface
  ([4f50f070](https://github.com/kotools/types/commit/4f50f070)).
- The `Integer` type now stores its internal representation using
  platform-specific types instead of `String`. This avoids parsing when
  performing arithmetic operations
  ([#912](https://github.com/kotools/types/issues/912), and
  [#914](https://github.com/kotools/types/pull/914) reviewed by
  [@daniel-rusu](https://github.com/daniel-rusu)).
- Optimize arithmetic operations of `Integer` for cases involving `0` and `1`
  ([#921](https://github.com/kotools/types/pull/921)).
- Error message of `Integer.Companion.fromInteger(Long)` **experimental**
  function in case of invalid input
  ([9dc7b938](https://github.com/kotools/types/commit/9dc7b938)).

### 🔥 Removed

- `orNull` and `orThrow` **deprecated** functions from `EmailAddress.Companion`
  and `EmailAddressRegex.Companion` **experimental** types. Use their `of`
  functions instead ([#871](https://github.com/kotools/types/issues/871) and
  [#872](https://github.com/kotools/types/issues/872)).
- Default serializer for `Integer` type
  ([0565df54](https://github.com/kotools/types/commit/0565df54)).

### 🐛 Fixed

- Inconsistency in structural equality operations of `Integer` **experimental**
  type, on Kotlin/Native
  ([855fd176](https://github.com/kotools/types/commit/855fd176)).

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
