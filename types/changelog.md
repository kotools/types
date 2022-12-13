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

## Version 3.2.0 - 2022/12/13

### All platforms

#### Added

- Explicit builders for all types
  ([#22](https://github.com/kotools/libraries/issues/22)).
- Types in the `kotools.types` package:
    - `NotBlankString` ([#103](https://github.com/kotools/libraries/issues/103))
    - `NonZeroInt` ([#104](https://github.com/kotools/libraries/issues/104))
    - `PositiveInt` ([#105](https://github.com/kotools/libraries/issues/105))
    - `NegativeInt` ([#106](https://github.com/kotools/libraries/issues/106))
    - `StrictlyPositiveInt`
      ([#107](https://github.com/kotools/libraries/issues/107))
    - `StrictlyNegativeInt`
      ([#108](https://github.com/kotools/libraries/issues/108))
    - `NotEmptyList` ([#109](https://github.com/kotools/libraries/issues/109))
    - `NotEmptySet` ([#110](https://github.com/kotools/libraries/issues/110))
    - `NotEmptyMap` ([#111](https://github.com/kotools/libraries/issues/111)).

#### Changed

- Update random API for getting a random `IntHolder`
  ([#17](https://github.com/kotools/libraries/issues/17)).
- Replace the `SinceKotoolsTypes` annotation by the `SinceKotools` shared
  annotation ([#54](https://github.com/kotools/libraries/issues/54)).

#### Deprecated

- The `random` function in the companion object of `IntHolder`'s subtypes
  ([#17](https://github.com/kotools/libraries/issues/17)).
- Builders throwing an exception without having the `OrThrow` suffix in their
  name ([#22](https://github.com/kotools/libraries/issues/22)).
- Builders having an invalid name, like `PositiveIntOrNull`
  ([#22](https://github.com/kotools/libraries/issues/22)).
- `ConstructionError` objects
  ([#22](https://github.com/kotools/libraries/issues/22)).
- The DSL in **alpha** stage for building numbers
  ([#33](https://github.com/kotools/libraries/issues/33)).
- The `NotBlankString` type from the `kotools.types.string` package
  ([#103](https://github.com/kotools/libraries/issues/103)).
- The following types from the `kotools.types.number` package:
    - `NonZeroInt` ([#104](https://github.com/kotools/libraries/issues/104))
    - `PositiveInt` ([#105](https://github.com/kotools/libraries/issues/105))
    - `NegativeInt` ([#106](https://github.com/kotools/libraries/issues/106))
    - `StrictlyPositiveInt`
      ([#107](https://github.com/kotools/libraries/issues/107))
    - `StrictlyNegativeInt`
      ([#108](https://github.com/kotools/libraries/issues/108)).
- The following types from the `kotools.types.collections` package:
    - `NotEmptyList` ([#109](https://github.com/kotools/libraries/issues/109))
    - `NotEmptySet` ([#110](https://github.com/kotools/libraries/issues/110))
    - `NotEmptyMap` ([#111](https://github.com/kotools/libraries/issues/111)).

## Version 3.1.1 - 2022/11/18

### All platforms

#### Changed

Remove error promotions of deprecated declarations from v3.1
([#55](https://github.com/kotools/libraries/issues/55)).

## Version 3.1.0 - 2022/10/24

### All platforms

#### Added

`NotEmptyMap` type representing maps that contain at least one entry
([#120](https://github.com/kotools/types/issues/120)).

#### Changed

- Convert the following types from a class to a sealed interface:
    - `NotBlankString` ([#124](https://github.com/kotools/types/issues/124))
    - `NotEmptyList` ([#125](https://github.com/kotools/types/issues/125))
    - `NotEmptySet` ([#126](https://github.com/kotools/types/issues/126)).
- Promote all deprecations of version 3.0
  ([#119](https://github.com/kotools/types/issues/119)).
- **Stabilize** the random API for getting an instance of `IntHolder` with a
  random value ([#118](https://github.com/kotools/types/issues/118)).
- The DSL for building numbers is now available in **alpha** stage in the
  `kotools.types.number` package
  ([#130](https://github.com/kotools/types/issues/130)).

## Version 3.0.1 - 2022/10/21

### All platforms

#### Changed

- Seal the `NotEmptyCollection` hierarchy
  ([#121](https://github.com/kotools/types/issues/121)).
- Override the `toString` function of `NotBlankString`
  ([#117](https://github.com/kotools/types/issues/117)) and `NotEmptySet`
  ([#123](https://github.com/kotools/types/issues/123)).

## Version 3.0.0 - 2022/10/16

### All platforms

#### Added

- Support for the JVM, JS and Native platforms using Kotlin Multiplatform
  ([#62](https://github.com/kotools/types/issues/62)).
- Serialization and deserialization with
  [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
  of `NonZeroInt`, `PositiveInt`, `StrictlyPositiveInt`, `NegativeInt`,
  `StrictlyNegativeInt`, `NotBlankString`, `NotEmptyList` and `NotEmptySet`
  ([#65](https://github.com/kotools/types/issues/65)).
- **Beta** API for getting an instance of `IntHolder` with a random value.

```kotlin
val x = PositiveInt.random()
val y = PositiveInt.random()
x.value == y.value // false
```

- **Experimental** DSL for building numbers according to the given context.
  Here's an example with the `nonZero` context:

```kotlin
nonZero int 1 // NonZeroInt(value = 1)
nonZero int 0 // throws an exception
nonZero intOrNull -1 // NonZeroInt(value = -1)
nonZero intOrNull 0 // null
```

#### Changed

- Update type system for integers with a new `IntHolder` type
  ([#112](https://github.com/kotools/types/issues/112)).

![Type system of integers v3](docs/int-type-system-v3.png)

- Builders of `NotEmptyList` and `NotEmptySet` now work without reifying types
  ([#68](https://github.com/kotools/types/issues/68)).
- Improve error management ([#70](https://github.com/kotools/types/issues/70)).

#### Deprecated

- `orNull` functions for building a subtype of `IntHolder`
  ([#112](https://github.com/kotools/types/issues/112)) and the `NotBlankString`
  type ([#113](https://github.com/kotools/types/issues/113)).

```kotlin
// Instead of doing...
NonZeroInt orNull 1
NotBlankString orNull "hello world"
// do this
NonZeroIntOrNull(1)
NotBlankStringOrNull("hello world")
```

- Constructor of `NotEmptyList` and `NotEmptySet`.

```kotlin
// Instead of doing...
NotEmptyList<Int>(1, 2, 3)
NotEmptySet<Int>(4, 5, 6)
// do this
notEmptyListOf<Int>(1, 2, 3)
notEmptySetOf<Int>(4, 5, 6)
```

- Positional access operations of `NotEmptyCollection` receiving an index of
  type `Int`.

```kotlin
val list = notEmptyListOf(1, 2, 3)
// Instead of doing...
list[1]
// do this
list[PositiveInt(1)] // or list[StrictlyPositiveInt(1)] 
```

#### Removed

- `NotEmptyMutableList` and `NotEmptyMutableSet`: mutable collections can be
  empty by definition.
- Conversions from subtypes of `IntHolder`.

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

Refactor binary operations of the following types in `kotools.types.number`:

- `NonZeroInt` ([#33](https://github.com/kotools/types/issues/33))
- `PositiveInt` ([#34](https://github.com/kotools/types/issues/34))
- `StrictlyPositiveInt` ([#35](https://github.com/kotools/types/issues/35))
- `NegativeInt` ([#36](https://github.com/kotools/types/issues/36))
- `StrictlyNegativeInt` ([#37](https://github.com/kotools/types/issues/37)).

## Version 1.2.1 - 2022/08/01

### Added

Add missing versioning annotations for version 1.2
([#56](https://github.com/kotools/types/issues/56)).

### Fixed

Fix the documentation of declarations in `kotools.types.string` and of
`toNotBlankString` functions in `kotools.types.number`
([#52](https://github.com/kotools/types/issues/52)).

## Version 1.2.0 - 2022/07/11

### Added

Implement a new `kotools.types.string.NotBlankString` type
([#28](https://github.com/kotools/types/issues/28)).

### Changed

Upgrade Gradle to `7.4.2` ([#29](https://github.com/kotools/types/issues/29)).

### Deprecated

Deprecate the old `io.github.kotools.types.string.NotBlankString` type and its
builders ([#28](https://github.com/kotools/types/issues/28)).

## Version 1.1.1 - 2022/08/01

### Fixed

- Fix the documentation of version 1.1
  ([#51](https://github.com/kotools/types/issues/51)).
- Fix usages of `@Throws` annotation
  ([#57](https://github.com/kotools/types/issues/57)).

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

Override `toString` function in all types
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
