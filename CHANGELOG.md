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

- The `EmailAddress` **experimental** type has a new constructor accepting a
  value of type `String` ([#623]).

```kotlin
// Before
EmailAddress.create("contact@kotools.org")

// Now
EmailAddress("contact@kotools.org")
```

- Introduced the `Zero` **experimental** type representing the zero number
  ([2df18e425], [43ee104d] and [5cf81497]).

### ♻️ Changed

- Our [versioning strategy](documentation/versioning-strategy.md) is now based
  on backward compatibility, focusing on **behavioral**, **source** and
  **binary** compatibilities ([5e2484b8b]).
- The `create` and the `createOrNull` **experimental** functions of the
  `NotBlankString.Companion` type now accept a value of type `Any` instead of
  `Any?` ([#626]).

```kotlin
// Before
NotBlankString.create(null) // pass
NotBlankString.createOrNull(null) // pass

// Now
NotBlankString.create(null) // compilation error
NotBlankString.createOrNull(null) // compilation error
```

- For simplicity purpose, the regular expression of the `EmailAddress`
  **experimental** type is now `^\S+@\S+\.\S+$` ([#600]). This is a
  **compatible behavioral** change that allows a wider range of values in email
  addresses.

```kotlin
// Before
EmailAddress.create("types-4_library@kotools.org") // exception

// Now
EmailAddress("types-4_library@kotools.org") // pass
```

### 🗑️ Deprecated

- Deprecation promotion of the following annotations to hidden ([#334]):
  `ExperimentalCollectionApi`, `ExperimentalNumberApi`, `ExperimentalRangeApi`,
  `ExperimentalResultApi` and `ExperimentalTextApi`.
- The `create(String)` and the `createOrNull(String)` **experimental** functions
  from the `EmailAddress.Companion` type are deprecated for using the new
  constructor for the `EmailAddress` type ([#623]).
  They are deprecated with an automatic replacement and an error level for
  removal in v4.7.

### 🔥 Removed

Due to an internal compilation error of Kotlin when comparing generics, the
following types have been removed from the **experimental** API:
`NotEmptyRange`, `Bound`, `InclusiveBound` and `ExclusiveBound` ([#627]).
**Experimental** properties using these types were also removed.

### 🐛 Fixed

Renames the `EmailAddress` **experimental** type's serializer as
`kotools.types.web.EmailAddress` ([#599]).

```kotlin
// Before
val serialName: String = serializer<EmailAddress>().descriptor.serialName
println(serialName) // kotools.types.experimental.EmailAddress

// Now
val serialName: String = serializer<EmailAddress>().descriptor.serialName
println(serialName) // kotools.types.web.EmailAddress
```

---

Thanks to [@augustomtt] and [@LVMVRQUXL] for contributing to this new release.
🙏

[@augustomtt]: https://github.com/augustomtt
[@LVMVRQUXL]: https://github.com/LVMVRQUXL
[#334]: https://github.com/kotools/types/issues/334
[#581]: https://github.com/kotools/types/issues/581
[#583]: https://github.com/kotools/types/issues/583
[#599]: https://github.com/kotools/types/issues/599
[#600]: https://github.com/kotools/types/issues/600
[#623]: https://github.com/kotools/types/issues/623
[#626]: https://github.com/kotools/types/pull/626
[#627]: https://github.com/kotools/types/pull/627
[2df18e425]: https://github.com/kotools/types/commit/2df18e425e3a889d0f0167708d02d9b887f4207c
[43ee104d]: https://github.com/kotools/types/commit/43ee104d05d8ca1bf764c7e3bc38b18373fe2b47
[5cf81497]: https://github.com/kotools/types/commit/5cf814977db2985d5adb87b21ef76c65df7cd5ed
[5e2484b8b]: https://github.com/kotools/types/commit/5e2484b8bf2756e41eb207d2e11acc9d5f5661d0

## 🔖 Releases

| Version | Release date |
|---------|--------------|
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
