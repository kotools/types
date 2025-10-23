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

> Thanks to [@LVMVRQUXL] for contributing to this new release. 🙏

### ✨ Added

- Support `iOS arm64` Kotlin Native target ([#843]).
- `Integer` type for solving overflow issues when adding, subtracting or
  multiplying integers ([#880]).
- `of(String)` and `of(String, EmailAddressRegex)` **experimental** functions in
  `EmailAddress.Companion` type, for creating an instance of `EmailAddress`
  ([#865]).
- `EmailAddressRegex.Companion.of(String)` **experimental** function for
  creating an instance of `EmailAddressRegex` ([#868]).
- Java compatibility in
  [dependency compatibility](documentation/dependencies.md) ([1dacbdae2]).

### ♻️ Changed

- Improve [declarations lifecycle](documentation/declarations-lifecycle.md)
  documentation ([1f9e8151a], [7a8e483f] and [fd2f9b28]).
- Update [dependency compatibility](documentation/dependencies.md) documentation
  ([c60c84d4]).

### 🗑️ Deprecated

- `orNull` and `orThrow` functions of `EmailAddress.Companion` **experimental**
  type, with an **error** level, for using its `of` functions instead ([#865]).
- `orNull(String)` and `orThrow(String)` functions of
  `EmailAddressRegex.Companion` **experimental** type, with an **error** level,
  for using its `of` function instead ([#868]).

### 🔥 Removed

- Following **deprecated** declarations from `EmailAddress.Companion`
  **experimental** type: `PATTERN` constant, `orNull(String, String)` and
  `orThrow(String, String)` functions ([#838]).
- `Zero` **experimental** type and its serializers ([#844]). Use `0` literal
  instead.

### 🐛 Fixed

- Run configurations in [contributing guidelines](CONTRIBUTING.md) ([#854]).
- Kotlin/Native typos in [README](README.md) of root project ([e4df56bf8]).

[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#838]: https://github.com/kotools/types/issues/838
[#843]: https://github.com/kotools/types/issues/843
[#844]: https://github.com/kotools/types/issues/844
[#854]: https://github.com/kotools/types/issues/854
[#865]: https://github.com/kotools/types/issues/865
[#868]: https://github.com/kotools/types/issues/868
[#880]: https://github.com/kotools/types/issues/880
[1dacbdae2]: https://github.com/kotools/types/commit/1dacbdae2
[1f9e8151a]: https://github.com/kotools/types/commit/1f9e8151a
[7a8e483f]: https://github.com/kotools/types/commit/7a8e483f
[c60c84d4]: https://github.com/kotools/types/commit/c60c84d4
[e4df56bf8]: https://github.com/kotools/types/commit/e4df56bf8
[fd2f9b28]: https://github.com/kotools/types/commit/fd2f9b28

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
