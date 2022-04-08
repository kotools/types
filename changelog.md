# Kotools Assert - Changelog

All notable changes to this project will be documented in this file.

> The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0)
> and this project adheres to
> [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## Work in progress

### Changed

- Split specific failure assertions `assertFailsWith` from untyped failure
  assertions `assertFails` ([#5](https://github.com/kotools/assert/issues/5)).

## Version 1.1.0 - 2022/02/12

### Added

- Failure assertions with `assertFails`
  ([#1](https://github.com/kotools/assert/issues/1)).

## Version 1.0.0 - 2022/02/12

### Added

- Equality assertions with `assertEquals` and `assertNotEquals`.
- Nullability assertions with `assertNull` and `assertNotNull`.
