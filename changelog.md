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

### Added

- Create and apply the `@SinceKotoolsAssert` annotation on every declaration
  ([#16](https://github.com/kotools/assert/issues/16)).
- Add boolean, nullability, equality and failure assertions in the
  `kotools.assert` package ([#15](https://github.com/kotools/assert/issues/15)).
- Add `@Test` and `@Nested` annotations
  ([#20](https://github.com/kotools/assert/issues/20)).

### Deprecated

- Deprecate nullability, equality and failure assertions in the
  `io.github.kotools.assert` package
  ([#15](https://github.com/kotools/assert/issues/15)).

## Version 2.0.0 - 2022/04/11

### Removed

- Equality and nullability assertions with lazy messages
  ([#11](https://github.com/kotools/assert/issues/11)).

## Version 1.2.0 - 2022/04/09

### Changed

- Split specific failure assertions `assertFailsWith` from untyped failure
  assertions `assertFails` ([#5](https://github.com/kotools/assert/issues/5)).

### Deprecated

- Equality and nullability assertions with lazy messages
  ([#5](https://github.com/kotools/assert/issues/5)).

### Removed

- Default messages from equality assertions
  ([#10](https://github.com/kotools/assert/issues/10)).

## Version 1.1.0 - 2022/02/12

### Added

- Failure assertions with `assertFails`
  ([#1](https://github.com/kotools/assert/issues/1)).

## Version 1.0.0 - 2022/02/12

### Added

- Equality assertions with `assertEquals` and `assertNotEquals`.
- Nullability assertions with `assertNull` and `assertNotNull`.
