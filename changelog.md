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

### Added

Introduce a new `NotEmptyCollection` hierarchy representing collections that
contain at least one element (issue
[#14](https://github.com/kotools/types/issues/14)).

```kotlin
interface NotEmptyCollection<out E>
class NotEmptyList<out E> : NotEmptyCollection<E>
class NotEmptySet<out E> : NotEmptyCollection<E>
```

### Changed

- Support for
  [Kotlin 1.5.32](https://github.com/JetBrains/kotlin/releases/tag/v1.5.32)
  (issue [#6](https://github.com/kotools/types/issues/6)).
- Update documentation of declarations returning the
  [`Result`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html)
  type (issue [#20](https://github.com/kotools/types/issues/20)).

## 4.0.0

### Added

Introduce a new `AnyInt` hierarchy representing integers (issue
[#132](https://github.com/kotools/libraries/issues/132) in [kotools/libraries]).

```kotlin
interface AnyInt
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
- Move the following types from the `kotools.types` package to the
  `kotools.types.collection` package with a new API: `NotEmptyList`,
  `NotEmptySet` and `NotEmptyMap` (issue
  [#138](https://github.com/kotools/libraries/issues/138) in
  [kotools/libraries]).
  Examples are available in the `Removed` section below.

### Removed

- Remove inheritance between the `NotBlankString` and the `Comparable` types
  (issue [#16](https://github.com/kotools/types/issues/8)).

```kotlin
val text: NotBlankString = "hello world".toNotBlankString().getOrThrow()
text as Comparable<NotBlankString> // before
"$text" as Comparable<String> // after
```

- Remove inheritance between the `NotEmptyList` and the `List` types (issue
  [#8](https://github.com/kotools/types/issues/8)).

```kotlin
val elements: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
elements as List<Int> // before
elements.toList() // after
```

- Remove inheritance between the `NotEmptySet` and the `Set` types (issue
  [#9](https://github.com/kotools/types/issues/9)).

```kotlin
val elements: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
elements as Set<Int> // before
elements.toSet() // after
```

- Remove inheritance between the `NotEmptyMap` and the `Map` types (issue
  [#10](https://github.com/kotools/types/issues/10)).

```kotlin
val entries: NotEmptyMap<String, Int> = notEmptyMapOf("a" to 1, "b" to 2)
entries as Map<String, Int> // before
entries.toMap() // after
```

- Remove all declarations from previous API while keeping the essentials: the
  types and their builder (issue
  [#37](https://github.com/kotools/libraries/issues/37) in [kotools/libraries]).

[kotools/libraries]: https://github.com/kotools/libraries
