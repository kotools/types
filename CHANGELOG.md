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

### ♻️ Changed

- Release type rules for **stable** and **experimental** declarations in the
  [versioning strategy].

### 🔥 Removed

- Implementation details from the documentation of `toString` for
  `NonZeroInteger`, `NonNegativeInteger` and `NonPositiveInteger`. ([#1048])

### 🐛 Fixed

- Unsatisfiable claim about distinct hash codes in the documentation of
  `hashCode` for `Decimal`, `Integer`, `NonNegativeInteger`,
  `NonPositiveInteger` and `NonZeroInteger`. ([#1051])

## 🔖 Archives

| Version | Release date |
|---------|--------------|
| [5.2.0] | 2026-06-28   |
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
[5.2.0]: https://github.com/kotools/types/releases/tag/5.2.0

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
[#992]: https://github.com/kotools/types/issues/992
[#998]: https://github.com/kotools/types/issues/998
[#999]: https://github.com/kotools/types/issues/999
[#1001]: https://github.com/kotools/types/issues/1001
[#1010]: https://github.com/kotools/types/issues/1010
[#1011]: https://github.com/kotools/types/issues/1011
[#1013]: https://github.com/kotools/types/issues/1013
[#1014]: https://github.com/kotools/types/issues/1014
[#1024]: https://github.com/kotools/types/issues/1024
[#1032]: https://github.com/kotools/types/issues/1032
[#1048]: https://github.com/kotools/types/issues/1048
[#1051]: https://github.com/kotools/types/issues/1051
