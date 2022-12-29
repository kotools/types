# Changelog

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

## Unreleased

## 4.0.0

### Added

Introduce a new `AnyInt` hierarchy representing integers (issue
[#132](https://github.com/kotools/libraries/issues/132) in [kotools/libraries]).

```kotlin
interface AnyInt : Comparable<AnyInt>
interface NonZeroInt : AnyInt
interface PositiveInt : AnyInt
interface NegativeInt : AnyInt
class StrictlyPositiveInt : NonZeroInt, PositiveInt
class StrictlyNegativeInt : NonZeroInt, NegativeInt
object ZeroInt : PositiveInt, NegativeInt
```

### Changed

- Move the `NotBlankString` type from the `kotools.types` package to the
  `kotools.types.text` package with a new API (issue
  [#133](https://github.com/kotools/libraries/issues/133) in
  [kotools/libraries]).

```kotlin
"Hello world".toNotBlankString() // before
"Hello world".asNotBlankString // before
```

- Move the following types from the `kotools.types` package to the
  `kotools.types.collection` package with a new API: `NotEmptyList`,
  `NotEmptySet` and `NotEmptyMap` (issue
  [#138](https://github.com/kotools/libraries/issues/138) in
  [kotools/libraries]).
  Examples are available in the `Removed` section below.

### Removed

- Remove inheritance between the `NotEmptyList` and the `List` types (issue
  [#8](https://github.com/kotools/types/issues/8)).

```kotlin
notEmptyListOf(1, 2, 3) as List<Int> // before
notEmptyListOf(1, 2, 3).asList // after
```

- Remove inheritance between the `NotEmptySet` and the `Set` types (issue
  [#9](https://github.com/kotools/types/issues/9)).

```kotlin
notEmptySetOf(1, 2, 3) as Set<Int> // before
notEmptySetOf(1, 2, 3).asSet // after
```

- Remove inheritance between the `NotEmptyMap` and the `Map` types (issue
  [#10](https://github.com/kotools/types/issues/10)).

```kotlin
notEmptyMapOf("a" to 1, "b" to 2, "c" to 3) as Map<String, Int> // before
notEmptyMapOf("a" to 1, "b" to 2, "c" to 3).asMap // after
```

- Remove all declarations from previous API while keeping the essentials: the
  types and their builder (issue
  [#37](https://github.com/kotools/libraries/issues/37) in [kotools/libraries]).

[kotools/libraries]: https://github.com/kotools/libraries
