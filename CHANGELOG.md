# 🔄 Changelog

All notable changes to this project will be documented in this file.

> The format of this document is based on [Keep a Changelog].

> See [the changelog in kotools/libraries] for versions `1`, `2` and `3`.

[keep a changelog]: https://keepachangelog.com/en/1.1.0
[the changelog in kotools/libraries]: https://github.com/kotools/libraries/blob/types-v3.2.0/types/changelog.md

## 🤔 Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## 🚧 Unreleased

### Added

- Support the macOS arm64 platform with Kotlin Native (issue
  [#414](https://github.com/kotools/types/issues/414)).
- The following **experimental** factory functions:
  - `create(Any?)` and `createOrNull(Any?)` in `NotBlankString.Companion` (issue
    [#341](https://github.com/kotools/types/issues/341))
  - `create(Number)` and `createOrNull(Number)` in
    `StrictlyPositiveInt.Companion` (issue
    [#342](https://github.com/kotools/types/issues/342)), in
    `StrictlyNegativeInt.Companion` (issue
    [#347](https://github.com/kotools/types/issues/347)), in
    `PositiveInt.Companion` (issue
    [#349](https://github.com/kotools/types/issues/349)), in
    `NegativeInt.Companion` (issue
    [#350](https://github.com/kotools/types/issues/350)), and in
    `NonZeroInt.Companion` (issue
    [#351](https://github.com/kotools/types/issues/351))
  - `create(Collection<E>)`, `createOrNull(Collection<E>)` and `of(E, vararg E)`
    in `NotEmptyList.Companion` (issue
    [#352](https://github.com/kotools/types/issues/352)) and in
    `NotEmptySet.Companion` (issue
    [#353](https://github.com/kotools/types/issues/353))
  - `create(Map<K, V>)`, `createOrNull(Map<K, V>)` and
    `of(Pair<K, V>, vararg Pair<K, V>)` in `NotEmptyMap.Companion` (issue
    [#354](https://github.com/kotools/types/issues/354)).
- Documentation of dependency compatibility (issue
  [#288](https://github.com/kotools/types/issues/288)).

### Changed

- Bump embedded Kotlin from 1.7.21 to 1.8.22 (issues
  [#172](https://github.com/kotools/types/issues/172) and
  [#196](https://github.com/kotools/types/issues/196)).
- Bump kotlinx.serialization from 1.4.0 to 1.5.1 (issues
  [#378](https://github.com/kotools/types/issues/378) and
  [#381](https://github.com/kotools/types/issues/381)).
- Move the `EmailAddress` **experimental** type from the
  `kotools.types.experimental` package to the new `kotools.types.web` one (issue
  [#377](https://github.com/kotools/types/issues/377)).
- Make the `regex` property of the `EmailAddress` **experimental** type
  inaccessible for Java sources, due to the unavailability of the
  `kotlin.text.Regex` type for this language (commit
  [8d0d098ad](https://github.com/kotools/types/commit/8d0d098ad)).
- Update the regular expression of the `EmailAddress` **experimental** type for
  following the
  [RFC-5322](https://datatracker.ietf.org/doc/html/rfc5322#section-3.4.1)
  (issue [#394](https://github.com/kotools/types/issues/394)).
- Update our Git commit messages convention using
  [Gitmoji](https://github.com/carloscuesta/gitmoji) in the contributing
  guidelines (issue [#490](https://github.com/kotools/types/issues/490)).

### Deprecated

Deprecation promotion of the following annotations to error (issue
[#333](https://github.com/kotools/types/issues/333)):
- `ExperimentalCollectionApi`
- `ExperimentalNumberApi`
- `ExperimentalRangeApi`
- `ExperimentalResultApi`
- `ExperimentalTextApi`

### Security

Upgrade follow-redirects to 1.15.4 on Kotlin/JS platform because prior versions
are vulnerable to Improper Input Validation due to the improper handling of URLs
by the `url.parse()` function (issue
[#375](https://github.com/kotools/types/issues/375)).
See the [security report](https://github.com/advisories/GHSA-jchw-25xp-jwwc) for
more details on this vulnerability.

## 🔖 Releases

- [4.4.2] - 2024-02-07
- [4.4.1] - 2024-02-02
- [4.4.0] - 2024-01-29
- [4.3.1] - 2023-09-25
- [4.3.0] - 2023-08-14
- [4.2.0] - 2023-06-24
- [4.1.0] - 2023-04-03
- [4.0.1] - 2023-02-06
- [4.0.0] - 2023-01-03
- [3.2.0] - 2022-12-13
- [3.1.1] - 2022-11-18
- [3.1.0] - 2022-10-24
- [3.0.1] - 2022-10-21
- [3.0.0] - 2022-10-16

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
