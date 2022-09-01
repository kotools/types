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

- Conversions from `String` and `NotBlankString` types to `NonZeroInt`,
  `PositiveInt`, `StrictlyPositiveInt`, `NegativeInt` and `StrictlyNegativeInt`
  ([#48](https://github.com/kotools/types/issues/48)).
- Serialization and deserialization with
  [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
  of `NonZeroInt`, `PositiveInt`, `StrictlyPositiveInt`, `NegativeInt`,
  `StrictlyNegativeInt`, `NotBlankString`, `NotEmptyList`,
  `NotEmptyMutableList`, `NotEmptySet`, `NotEmptyMutableSet` and
  `NotEmptyCollection` ([#65](https://github.com/kotools/types/issues/65)).
- `KotoolsInt` type for unifying integer's types
  ([#60](https://github.com/kotools/types/issues/60)).
- Random number retrieving from the ranges of `NonZeroInt`, `PositiveInt` and
  `NegativeInt` ([#79](https://github.com/kotools/types/issues/79)).

### Changed

- Builders of `NotEmptyList`, `NotEmptyMutableList`, `NotEmptySet` and
  `NotEmptyMutableSet` now work without reifying type
  ([#68](https://github.com/kotools/types/issues/68)).
- Improve error messages ([#70](https://github.com/kotools/types/issues/70)).

### Fixed

- Binary operations with bad return types in `PositiveInt`,
  `StrictlyPositiveInt`, `NegativeInt` and `StrictlyNegativeInt`
  ([#69](https://github.com/kotools/types/issues/69)).

## Version 2.0.0 - 2022/08/01

### Added

- Overload `Int` comparisons with `StrictlyPositiveInt`, `NegativeInt` and
  `StrictlyNegativeInt` ([#48](https://github.com/kotools/types/issues/48)).
- Add comparisons between `String` and `NotBlankString`
  ([#58](https://github.com/kotools/types/issues/58)).

### Changed

- Integer's types in `kotools.types.number` now implements `Comparable<Int>`
  ([#48](https://github.com/kotools/types/issues/48)).
- The type `NotBlankString` now implements `Comparable<String>`
  ([#58](https://github.com/kotools/types/issues/58)).

### Removed

- Remove the old `io.github.kotools.types.string.NotBlankString` type and its
  builders ([#41](https://github.com/kotools/types/issues/41)).
- Remove the old `NonZeroInt`, `StrictlyPositiveInt` and `StrictlyNegativeInt`
  types in `io.github.kotools.types.int`
  ([#45](https://github.com/kotools/types/issues/45)).

## Version 1.3.1 - 2022/08/01

### Fixed

- Fix the documentation of `NotEmptyList` and `NotEmptySet` types
  ([#46](https://github.com/kotools/types/issues/46)).
- Fix the documentation of comparisons in `kotools.types.number` and of
  `NotEmptyCollection` ([#49](https://github.com/kotools/types/issues/49)).

## Version 1.2.1 - 2022/08/01

### Added

- Add missing versioning annotations for version 1.2
  ([#56](https://github.com/kotools/types/issues/56)).

### Fixed

- Fix the documentation of declarations in `kotools.types.string` and of
  `toNotBlankString` functions in `kotools.types.number`
  ([#52](https://github.com/kotools/types/issues/52)).

## Version 1.1.1 - 2022/08/01

### Fixed

- Fix the documentation of version 1.1
  ([#51](https://github.com/kotools/types/issues/51)).
- Fix usages of `@Throws` annotation
  ([#57](https://github.com/kotools/types/issues/57)).

## Version 1.3.0 - 2022/07/27

### Added

- Implement the following types in `kotools.types.collections`:
    - `NotEmptyList` ([#6](https://github.com/kotools/types/issues/6))
    - `NotEmptyCollection` and `NotEmptySet`
      ([#7](https://github.com/kotools/types/issues/7))
    - `NotEmptyMutableList`
      ([#12](https://github.com/kotools/types/issues/12))
    - `NotEmptyMutableSet`
      ([#13](https://github.com/kotools/types/issues/13)).
- Overload comparisons of the following types in `kotools.types.number`:
    - `NonZeroInt` ([#33](https://github.com/kotools/types/issues/33))
    - `PositiveInt` ([#34](https://github.com/kotools/types/issues/34))
    - `StrictlyPositiveInt` ([#35](https://github.com/kotools/types/issues/35))
    - `NegativeInt` ([#36](https://github.com/kotools/types/issues/36))
    - `StrictlyNegativeInt` ([#37](https://github.com/kotools/types/issues/37)).

### Changed

- Refactor binary operations of the following types in `kotools.types.number`:
    - `NonZeroInt` ([#33](https://github.com/kotools/types/issues/33))
    - `PositiveInt` ([#34](https://github.com/kotools/types/issues/34))
    - `StrictlyPositiveInt` ([#35](https://github.com/kotools/types/issues/35))
    - `NegativeInt` ([#36](https://github.com/kotools/types/issues/36))
    - `StrictlyNegativeInt` ([#37](https://github.com/kotools/types/issues/37)).

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
