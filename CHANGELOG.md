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

- Support iOS Simulator arm64 Kotlin Native target ([#415]).
- Dependency upgrades strategy in the
  [dependency compatibility](documentation/dependencies.md) documentation
  ([#289]). 

### ♻️ Changed

- Bump embedded Kotlin from 1.8.22 to 1.9.25, Kotlin language version from 1.5
  to 1.9, and kotlinx.serialization from 1.5.1 to 1.6.3 ([#407] and [#740]).
- Use default package names for Linux x64, macOS x64 and MinGW x64 ([#405]).
- Move `ExperimentalKotoolsTypesApi` from the `kotools.types.experimental`
  package to the `org.kotools.types` one ([#636]).

### 🔥 Removed

- `KotoolsTypesSerializers.zero` **deprecated** property from the
  **experimental** API ([#723]).
- `KotoolsTypesSerializers.emailAddress` **deprecated** property, from the
  **experimental** API ([#727]).
- **Hidden** annotations from the `kotools.types.experimental` package ([#318]).
- `Zero.Companion.fromByte` and `Zero.Companion.fromByteOrNull` **deprecated**
  methods from the **experimental** API ([#715]).
- `EmailAddress.Companion.fromString` and
  `EmailAddress.Companion.fromStringOrNull` **deprecated** methods from the
  **experimental** API ([#721]).

### 🐛 Fixed

- Links to the API reference from the README documentation ([#742]).

---

Thanks to [@bodiam] and [@LVMVRQUXL] for contributing to this new release. 🙏

[@bodiam]: https://github.com/bodiam
[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#289]: https://github.com/kotools/types/issues/289
[#318]: https://github.com/kotools/types/issues/318
[#405]: https://github.com/kotools/types/issues/405
[#407]: https://github.com/kotools/types/issues/407
[#415]: https://github.com/kotools/types/issues/415
[#636]: https://github.com/kotools/types/issues/636
[#715]: https://github.com/kotools/types/issues/715
[#721]: https://github.com/kotools/types/issues/721
[#723]: https://github.com/kotools/types/issues/723
[#727]: https://github.com/kotools/types/issues/727
[#740]: https://github.com/kotools/types/issues/740
[#742]: https://github.com/kotools/types/pull/742

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
