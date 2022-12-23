# Kotools Types - Changelog

All notable changes to this project will be documented in this file.

> The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0)
> and this project adheres to
> [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

> The changelog of Kotools Types 1, 2 and 3 is available
> [here](https://github.com/kotools/libraries/blob/types-v3.2.0/types/changelog.md).

## Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## Work in progress

### All platforms

#### Added

- Hierarchy of `AnyInt` representing integers
  ([#132](https://github.com/kotools/libraries/issues/132)).
- The `length` property to the `NotBlankString` type
  ([#143](https://github.com/kotools/libraries/issues/143)).

#### Changed

- Move the `NotBlankString` type to the `kotools.types.text` package
  ([#133](https://github.com/kotools/libraries/issues/133)).
- Move the following types to the `kotools.types.collection` package:
  `NotEmptyList`, `NotEmptySet` and `NotEmpty`
  ([#138](https://github.com/kotools/libraries/issues/138)).

#### Removed

Deprecated declarations from version 3
([#37](https://github.com/kotools/libraries/issues/37)).
