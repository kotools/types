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

- Introduced the `Zero` **experimental** type representing the zero number in
  the new `org.kotools.types` package.
  This package will contain reimplemented types and those from the
  `kotools.types.*` packages will be deprecated incrementally.
- Introduced the `EmailAddress` **experimental** type representing email
  addresses in the `org.kotools.types` package ([#635]).
  This new implementation provides new `fromString` and `fromStringOrNull`
  factory functions accepting a `value` argument of type `Any`.
  For simplicity purpose, the default pattern used for validating email
  addresses is `^\S+@\S+\.\S+$`, which allows a wider range of values.
  But it is also possible to provide a `pattern` argument of type `Any` to these
  new factory functions for customizing the validation.

```kotlin
val value: Any = "contact@kotools.org"
val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
val result: Result<EmailAddress> = kotlin.runCatching {
    EmailAddress.fromString(value, pattern)
}
println(result.isSuccess) // true
```

### ♻️ Changed

- Our [versioning strategy](documentation/versioning-strategy.md) is now based
  on backward compatibility, focusing on **behavioral**, **source** and
  **binary** compatibilities.
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

### 🗑️ Deprecated

- The following annotations are now **hidden** from sources ([#334]):
  `ExperimentalCollectionApi`, `ExperimentalNumberApi`, `ExperimentalRangeApi`,
  `ExperimentalResultApi` and `ExperimentalTextApi`.
- The `EmailAddress` **experimental** type from the `kotools.types.web` package
  is now deprecated with a **warning level** for using the corresponding type
  from the `org.kotools.types` package ([#635]).
  Its `create` and `createOrNull` factory functions are also deprecated with an
  **error level** for this reason.
  These deprecated declarations will be removed in v4.7.

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
[#599]: https://github.com/kotools/types/issues/599
[#626]: https://github.com/kotools/types/pull/626
[#627]: https://github.com/kotools/types/pull/627
[#635]: https://github.com/kotools/types/issues/635

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
