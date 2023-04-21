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

The `plus` operations for concatenating a `NotBlankString` with a `String` or a
`Char` (issue [#53](https://github.com/kotools/types/issues/53)).

```kotlin
resultOf {
    val firstString: NotBlankString = "hello".toNotBlankString()
    val secondString: NotBlankString = "world".toNotBlankString()
    var result: NotBlankString
    // before
    result = ("$firstString" + 'a').toNotBlankString()
    result = ("$firstString" + "everyone").toNotBlankString()
    result = ("$firstString" + "$secondString").toNotBlankString()
    // after
    result = firstString + 'a'
    result = firstString + "everyone"
    result = firstString + secondString
}
```

### Changed

- Support for
  [Kotlin 1.6.21](https://github.com/JetBrains/kotlin/releases/tag/v1.6.21) and
  [kotlinx.serialization 1.3.3](https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.3.3)
  (issue [#51](https://github.com/kotools/types/issues/51)).
- Relocate the library from `io.github.kotools` to `org.kotools` (issue
  [#63](https://github.com/kotools/types/issues/63)). Here's an example using
  the Kotlin DSL in Gradle:

```kotlin
// before
implementation("io.github.kotools:types:$version")
// after
implementation("org.kotools:types:$version")
```

## 4.1.0

_Release date: 2023-04-03._

### Added

- `NotEmptyCollection` hierarchy representing collections that contain at least
  one element (issue [#14](https://github.com/kotools/types/issues/14)).

```kotlin
interface NotEmptyCollection<out E>
class NotEmptyList<out E> : NotEmptyCollection<E>
class NotEmptySet<out E> : NotEmptyCollection<E>
```

- Binary operations (`plus`, `minus`, `times`, `div` and `rem`) for the `AnyInt`
  hierarchy (issue [#31](https://github.com/kotools/types/issues/31)).

```kotlin
val x: AnyInt = NonZeroInt.random()
val y: AnyInt = PositiveInt.random()
var result: Int
// before
result = 1 + x.toInt()
result = x.toInt() + 1
result = x.toInt() + y.toInt()
// after
result = 1 + x
result = x + 1
result = x + y
```

- `resultOf` function for encapsulating computations of functions returning the
  [`Result`][kotlin.result] type (issue
  [#37](https://github.com/kotools/types/issues/37)).

```kotlin
data class Person(val name: NotBlankString, val age: StrictlyPositiveInt)

var somebody: Person
// before
somebody = Person(
    name = "John Doe".toNotBlankString().getOrThrow(),
    age = 42.toStrictlyPositiveInt().getOrThrow()
)
// after
somebody = resultOf {
    Person(
        name = "John Doe".toNotBlankString(),
        age = 42.toStrictlyPositiveInt()
    )
}
```

### Changed

- Support for
  [Kotlin 1.5.32](https://github.com/JetBrains/kotlin/releases/tag/v1.5.32)
  (issue [#6](https://github.com/kotools/types/issues/6)).
- The following builders now works on the
  [`Number`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-number/index.html)
  type instead of the
  [`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)
  type only: `toNonZeroInt`, `toPositiveInt`, `toNegativeInt`,
  `toStrictlyPositiveInt` and `toStrictlyNegativeInt` (issue
  [#43](https://github.com/kotools/types/issues/43)).

```kotlin
val x: Double = 0.1
var result: Result<StrictlyPositiveInt>
// before
result = x.toInt().toStrictlyPositiveInt()
// after
result = x.toStrictlyPositiveInt()
```

- The `AnyInt` and the `NotBlankString` types are now inheriting from
  [`Comparable`][kotlin.comparable] (issue
  [#45](https://github.com/kotools/types/issues/45)).

```kotlin
val firstInt: AnyInt = StrictlyPositiveInt.random()
val secondInt: AnyInt = StrictlyNegativeInt.random()
val firstString: NotBlankString = "hello".toNotBlankString()
    .getOrThrow()
val secondString: NotBlankString = "world".toNotBlankString()
    .getOrThrow()
var result: Boolean
// before
result = firstInt.toInt() > secondInt.toInt()
result = "$firstString" < "$secondString"
// after
result = firstInt > secondInt
result = firstString < secondString
```

## 4.0.1

_Release date: 2023-02-06._

### Changed

Update documentation of declarations returning the [`Result`][kotlin.result]
type (issue [#20](https://github.com/kotools/types/issues/20)).

### Fixed

Fix versioning annotation of declarations in the `kotools.types.number` package
(issue [#23](https://github.com/kotools/types/issues/23)).

## 4.0.0

_Release date: 2023-01-03._

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

- Remove inheritance between the `NotBlankString` and the
  [`Comparable`][kotlin.comparable] types (issue
  [#16](https://github.com/kotools/types/issues/8)).

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

[kotlin.comparable]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable

[kotlin.result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html
