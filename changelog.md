# Kotools Types - Changelog

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

- Implement a new `NotEmptyList` type in `kotools.types.collections`
  ([#6](https://github.com/kotools/types/issues/6)).
- Overload comparisons of `kotools.types.number.NonZeroInt`
  ([#33](https://github.com/kotools/types/issues/33)).
- Overload comparisons of `kotools.types.number.PositiveInt`
  ([#34](https://github.com/kotools/types/issues/34)).

### Changed

- Refactor binary operations of `kotools.types.number.NonZeroInt`
  ([#33](https://github.com/kotools/types/issues/33)).
- Refactor binary operations of `kotools.types.number.PositiveInt`
  ([#34](https://github.com/kotools/types/issues/34)).
- Refactor binary operations of `kotools.types.number.StrictlyPositiveInt`
  ([#35](https://github.com/kotools/types/issues/35)).

## Version 1.2.0 - 2022/07/11

### Added

- Implement a new `kotools.types.string.NotBlankString` type
  ([#28](https://github.com/kotools/types/issues/28)).

### Changed

- Upgrade Gradle to `7.4.2` ([#29](https://github.com/kotools/types/issues/29)).

### Deprecated

- Deprecate the old `io.github.kotools.types.string.NotBlankString` type and its
  builders ([#28](https://github.com/kotools/types/issues/28)).

## Version 1.1.0 - 2022/07/09

### Added

- Implement and apply `SinceKotoolsTypes` annotation on every declarations
  ([#23](https://github.com/kotools/types/issues/23)).
- Implement a new `kotools.types.number.NonZeroInt` type
  ([#16](https://github.com/kotools/types/issues/16)).
- Implement `kotools.types.number.PositiveInt` type
  ([#14](https://github.com/kotools/types/issues/14)).
- Implement `kotools.types.number.StrictlyPositiveInt` type
  ([#18](https://github.com/kotools/types/issues/18)).
- Implement `kotools.types.number.NegativeInt` type
  ([#15](https://github.com/kotools/types/issues/15)).
- Implement `kotools.types.number.StrictlyNegativeInt` type
  ([#19](https://github.com/kotools/types/issues/19)).

### Deprecated

- Deprecate the old `io.github.kotools.types.int.NonZeroInt` type and its
  builders ([#16](https://github.com/kotools/types/issues/16)).
- Deprecate the old `io.github.kotools.types.int.StrictlyPositiveInt` type and
  its builders ([#18](https://github.com/kotools/types/issues/18)).
- Deprecate the old `io.github.kotools.types.int.StrictlyNegativeInt` type and
  its builders ([#19](https://github.com/kotools/types/issues/19)).

## Version 1.0.1 - 2022/03/21

### Fixed

- Override `toString` function in all types
  ([#9](https://github.com/kotools/types/issues/9)).

## Version 1.0.0 - 2022/02/28

### Added

- Implement `NonZeroInt` type ([#3](https://github.com/kotools/types/issues/3)).
- Implement `StrictlyNegativeInt` type
  ([#4](https://github.com/kotools/types/issues/4)).
- Implement `StrictlyPositiveInt` type
  ([#1](https://github.com/kotools/types/issues/1)).
- Implement `NotBlankString` type
  ([#5](https://github.com/kotools/types/issues/5)).
