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

- The following declarations to the `Zero` **experimental** type:
  - `compareTo(Byte)`, `compareTo(Short)`, `compareTo(Int)`, `compareTo(Long)`,
    `compareTo(Float)` and `compareTo(Double)` functions ([#650])
  - `toByte()`, `toShort()`, `toInt()`, `toLong()`, `toFloat()`, `toDouble()`
    and `toChar()` functions ([#646]).
- Samples to the documentation of the `Zero()` constructor ([7c8172d2]).

### ♻️ Changed

- The `value` parameter of the `fromString` and the `fromStringOrNull` functions
  from the `EmailAddress.Companion` **experimental** type, that is now named
  `text` ([#682]).
  This is an **incompatible source change**.
- Samples in the API reference for using assertions instead of printing values
  ([#641]) and collapsing them by default ([#678]).
- Kotlin and Java samples of the
  `StrictlyNegativeDouble.Companion.create(Number)` function in the API
  reference ([0a4258f3]).
- Documentation of the `EmailAddress.Companion.PATTERN` property ([1a0ea52b] and
  [fd003c4a]).
- Documentation of the `Zero` type, the `Zero.Companion.fromByte(Byte)` and the
  `Zero.Companion.fromByteOrNull(Byte)` functions ([#681] and [ccaaa3a28]).
- Documentation of the `EmailAddress` type ([f1ac24ca8]) and the
  `EmailAddress.Companion.fromString` functions ([4c188db70]).

### 🐛 Fixed

- Kotlin samples of the `NotEmptyMap.Companion.create` ([1fa89b0c8]) and the
  `NotEmptyMap.Companion.createOrNull` functions ([35d599bcc]) in the API
  reference.
- Java sample of the `StrictlyNegativeDouble.equals(Any?)` **experimental**
  function in the API reference ([c862e0cac]).

### 🔥 Removed

- The `kotools.types.web.EmailAddress` **deprecated** type from the
  **experimental** API ([#663]).
- Availability of the `Zero.Companion.fromByteOrNull(Byte)` **experimental**
  function from Java code ([#683]).
- Availability of the `EmailAddress.Companion.fromStringOrNull` **experimental**
  functions from Java code ([#684]).

---

Thanks to [@LVMVRQUXL] and [@MartiPresa] for contributing to this new release.
🙏

[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[@MartiPresa]: https://github.com/MartiPresa
[#641]: https://github.com/kotools/types/issues/641
[#646]: https://github.com/kotools/types/issues/646
[#650]: https://github.com/kotools/types/issues/650
[#663]: https://github.com/kotools/types/pull/663
[#678]: https://github.com/kotools/types/issues/678
[#681]: https://github.com/kotools/types/issues/681
[#682]: https://github.com/kotools/types/issues/682
[#683]: https://github.com/kotools/types/issues/683
[#684]: https://github.com/kotools/types/issues/684
[0a4258f3]: https://github.com/kotools/types/commit/0a4258f3
[1a0ea52b]: https://github.com/kotools/types/commit/1a0ea52b
[1fa89b0c8]: https://github.com/kotools/types/commit/1fa89b0c8
[4c188db70]: https://github.com/kotools/types/commit/4c188db70
[7c8172d2]: https://github.com/kotools/types/commit/7c8172d2
[35d599bcc]: https://github.com/kotools/types/commit/35d599bcc
[c862e0cac]: https://github.com/kotools/types/commit/c862e0cac
[ccaaa3a28]: https://github.com/kotools/types/commit/ccaaa3a28
[f1ac24ca8]: https://github.com/kotools/types/commit/f1ac24ca8
[fd003c4a]: https://github.com/kotools/types/commit/fd003c4a

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
