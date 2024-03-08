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

- Support macOS arm64 systems with Kotlin Native (#414).
- The `StrictlyNegativeDouble` **experimental** type for representing a
  floating-point number of type [Double][kotlin.Double] that is less than zero
  (#555).
- The `create(Any?)` and the `createOrNull(Any?)` **experimental** factory
  functions in `NotBlankString.Companion` (#341).
- The `create(Number)` and `createOrNull(Number)` **experimental** factory
  functions in the following types:
  - `StrictlyPositiveInt.Companion` (#342)
  - `StrictlyNegativeInt.Companion` (#347)
  - `PositiveInt.Companion` (#349)
  - `NegativeInt.Companion` (#350)
  - `NonZeroInt.Companion` (#351).
- The `create(Collection<E>)`, the `createOrNull(Collection<E>)` and the
  `of(E, vararg E)` **experimental** factory functions in
  `NotEmptyList.Companion` (#352) and in `NotEmptySet.Companion` (#353).
- The `create(Map<K, V>)`, the `createOrNull(Map<K, V>)` and
  the `of(Pair<K, V>, vararg Pair<K, V>)` **experimental** factory functions in
  `NotEmptyMap.Companion` (#354).
- Documentation of dependency compatibility (#288).
- The documentation of the serialization and the deserialization processes in
  the [API reference] for the following types: `AnyInt`, `NonZeroInt`,
  `PositiveInt`, `NegativeInt`, `StrictlyPositiveInt`, `StrictlyNegativeInt`,
  `ZeroInt`, `NotBlankString`, `NotEmptyList`, `NotEmptySet` and `NotEmptyMap`
  (#256).
- References to the corresponding factory functions for eligible types in the
  [API reference] (cfc99b213).
- Tagline suggested by @jmfayard in the [README](README.md) documentation
  (#338).

[api reference]: https://types.kotools.org
[kotlin.Double]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html

### ♻️ Changed

- Bump embedded Kotlin from 1.7.21 to 1.8.22 (#172 and #196).
- Bump kotlinx.serialization from 1.4.0 to 1.5.1 (#378 and #381).
- Move the `EmailAddress` **experimental** type from the
  `kotools.types.experimental` package to the new `kotools.types.web` one
  (#377).
- Make the `regex` property of the `EmailAddress` **experimental** type
  inaccessible for Java sources, due to the unavailability of the
  `kotlin.text.Regex` type for this language (8d0d098ad).
- Update the regular expression of the `EmailAddress` **experimental** type for
  following the [RFC-5322] (#394).
- Update our Git commit messages convention using [Gitmoji] in our
  [Contribution Guidelines](CONTRIBUTING.md) (#490).

[Gitmoji]: https://github.com/carloscuesta/gitmoji
[RFC-5322]: https://datatracker.ietf.org/doc/html/rfc5322#section-3.4.1

### 🗑️ Deprecated

Deprecation promotion of the following annotations to error (#333):

- `ExperimentalCollectionApi`
- `ExperimentalNumberApi`
- `ExperimentalRangeApi`
- `ExperimentalResultApi`
- `ExperimentalTextApi`

---

Thanks to @LVMVRQUXL and @jmfayard for contributing to this new release. 🙏

Happy coding with Kotools Types! 🎉

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
