# Changelog

All notable changes to this project will be documented in this file.

> The format of this document is based on [Keep a Changelog].
>
> See [the changelog in kotools/libraries] for versions `1`, `2` and `3`.

[keep a changelog]: https://keepachangelog.com/en/1.1.0
[the changelog in kotools/libraries]: https://github.com/kotools/libraries/blob/types-v3.2.0/types/changelog.md

## Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## Unreleased

### Added

#### Experimental annotation for collections

This version introduces an `ExperimentalCollectionApi` annotation in the
`kotools.types.experimental` package for marking experimental declarations
of the `kotools.types.collection` package (issue [#177] fixed by PRs [#183] and
[#201]).

[#177]: https://github.com/kotools/types/issues/177
[#183]: https://github.com/kotools/types/pull/183
[#201]: https://github.com/kotools/types/pull/201

#### Builders suffixed by `OrNull` and `OrThrow`

For aligning with Kotlin standards incrementally, like discussed in the idea
[#104], we've added **experimental** builders that, in case of a failure,
should return `null` if the builder is suffixed by `OrNull` or throw an
exception if it's suffixed by `OrThrow`.
This change applies for the following types:

- `StrictlyPositiveInt` (issue [#141] fixed by PRs [#164] and [#202])
- `StrictlyNegativeInt` (issue [#149] fixed by PRs [#181] and [#203], and by
  [@o-korpi] in PR [#167])
- `NotBlankString` (issue [#174] fixed by PRs [#182] and [#204]).

Here's an example for the `StrictlyPositiveInt` type:

```kotlin
// before
1.toStrictlyPositiveInt().getOrNull()
2.toStrictlyPositiveInt().getOrThrow()
// after
1.toStrictlyPositiveIntOrNull()
2.toStrictlyPositiveIntOrThrow()
```

[#104]: https://github.com/kotools/types/discussions/104
[#141]: https://github.com/kotools/types/issues/141
[#149]: https://github.com/kotools/types/issues/149
[#164]: https://github.com/kotools/types/pull/164
[#167]: https://github.com/kotools/types/pull/167
[#174]: https://github.com/kotools/types/issues/174
[#181]: https://github.com/kotools/types/pull/181
[#182]: https://github.com/kotools/types/pull/182
[#202]: https://github.com/kotools/types/pull/202
[#203]: https://github.com/kotools/types/pull/203
[#204]: https://github.com/kotools/types/pull/204
[@o-korpi]: https://github.com/o-korpi

### Changed

#### Source compatibility with Kotlin

In this new version, we've improved the source compatibility with Kotlin by
supporting its versions 1.5 through 1.7 (PR [#213]).

Please note that since [Kotools Types 4.3.0], this project is compiled with
[Kotlin 1.7.21] by default.

[#213]: https://github.com/kotools/types/pull/213
[Kotools Types 4.3.0]: https://github.com/kotools/types/releases/tag/4.3.0

#### Multiple versions in API reference

The configuration of the [API reference] was improved for supporting multiple
versions, starting from version [4.2.0][tag/4.2.0] (PR [#198] and issue [#205]
fixed by PR [#206]).
We recommend you to only use the versions documented in the API reference for a
better experience while working with Kotools Types.

The [API reference] is also securely deployed using an [SSH deploy key] when a
release is published (issue [#207] fixed by PR [#208]).

[#198]: https://github.com/kotools/types/pull/198
[#205]: https://github.com/kotools/types/issues/205
[#206]: https://github.com/kotools/types/pull/206
[#207]: https://github.com/kotools/types/issues/207
[#208]: https://github.com/kotools/types/pull/208
[API reference]: https://types.kotools.org
[SSH deploy key]: https://docs.github.com/fr/authentication/connecting-to-github-with-ssh/managing-deploy-keys#deploy-keys

#### Centralizing Gradle plugins and dependencies

For reducing the overall complexity of the
[Gradle build script](build.gradle.kts), we've centralized the plugins and the
dependencies in a [version catalog](gradle/libs.versions.toml) instead of
defining them in the build script (PR [#199]).

Read the _[TOML + Gradle + project accessors]_ article by [@FunkyMuse] for
more information about how to set up a version catalog with a [TOML] file.

[#199]: https://github.com/kotools/types/pull/199
[@FunkyMuse]: https://github.com/FunkyMuse
[TOML + Gradle + project accessors]: https://funkymuse.dev/posts/toml-gradle
[TOML]: https://toml.io

### Fixed

#### Documentation typo

We've fixed a little typo in the documentation of the `notEmptyRangeOf`
function (PR [#222]).

[#222]: https://github.com/kotools/types/pull/222

## 4.3.0

_Release date: 2023-08-14._

_See the [full changelog][4.2.0-4.3.0] from version [`4.2.0`][tag/4.2.0]._

[4.2.0-4.3.0]: https://github.com/kotools/types/compare/4.2.0...4.3.0
[tag/4.2.0]: https://github.com/kotools/types/releases/tag/4.2.0

### Changed

- Support for [Kotlin 1.7.21] and [kotlinx.serialization 1.4.1] (issue [#142]).

[#142]: https://github.com/kotools/types/issues/142
[Kotlin 1.7.21]: https://github.com/JetBrains/kotlin/releases/tag/v1.7.21
[kotlinx.serialization 1.4.1]: https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.4.1

- Convert the `NotEmptyList` (issue [#136]), the `NotEmptySet` (issue [#137])
  and the `NotEmptyMap` (issue [#138]) types to inline classes.

[#136]: https://github.com/kotools/types/issues/136
[#137]: https://github.com/kotools/types/issues/137
[#138]: https://github.com/kotools/types/issues/138

## 4.2.0

_Release date: 2023-06-24._

_See the [full changelog][4.1.0-4.2.0] from version [`4.1.0`][tag/4.1.0]._

[4.1.0-4.2.0]: https://github.com/kotools/types/compare/4.1.0...4.2.0
[tag/4.1.0]: https://github.com/kotools/types/releases/tag/4.1.0

### Added

- The `NotEmptyRange` and the `Bound` **experimental** types representing a
  range of comparable values that contain at least one value (issue [#56]).

```kotlin
val range = notEmptyRangeOf<Int> { 1.inclusive to 42.exclusive }
println(range) // [1;42[
println(3 in range) // true
println(42 in range) // false
```

[#56]: https://github.com/kotools/types/issues/56

- The `StrictlyPositiveDouble` **experimental** type representing strictly
  positive floating-point numbers represented by the `Double` type (issue
  [#66]).

```kotlin
val x: StrictlyPositiveDouble = 0.5.toStrictlyPositiveDouble()
    .getOrThrow()
println(x) // 0.5
```

[#66]: https://github.com/kotools/types/issues/66

- The `plus` **experimental** operations for concatenating a `NotBlankString`
  with a `String` or a `Char` (issue [#53]).

```kotlin
val firstString: NotBlankString = "hello".toNotBlankString().getOrThrow()
val secondString: NotBlankString = "world".toNotBlankString().getOrThrow()
var result: NotBlankString
// before
result = ('a' + "$firstString" + 'b').toNotBlankString().getOrThrow()
result = ("$firstString" + "everyone").toNotBlankString().getOrThrow()
result = ("$firstString" + "$secondString").toNotBlankString().getOrThrow()
// after
result = 'a' + "$firstString" + 'b'
result = firstString + "everyone"
result = firstString + secondString
```

[#53]: https://github.com/kotools/types/issues/53

- The `Result.flatMap` **experimental** operation for transforming its
  encapsulated value (issue [#47]).

```kotlin
var result: Result<StrictlyPositiveInt>
// before
result = 3.toNonZeroInt()
    .mapCatching { (it * 2).toStrictlyPositiveInt().getOrThrow() }
// after
result = 3.toNonZeroInt()
    .flatMap { (it * 2).toStrictlyPositiveInt() }
```

[#47]: https://github.com/kotools/types/issues/47

- The `unaryMinus` **experimental** operations for returning the negative of an
  `AnyInt` (issue [#54] implemented by [@MichaelStH]).

```kotlin
val x: NonZeroInt = 1.toNonZeroInt().getOrThrow()
// before
x.toInt()
    .unaryMinus()
    .toNonZeroInt()
    .getOrThrow()
    .let(::println) // -1
// after
println(-x) // -1
```

[#54]: https://github.com/kotools/types/issues/54
[@MichaelStH]: https://github.com/MichaelStH

### Changed

- Support for [Kotlin 1.6.21] and [kotlinx.serialization 1.3.3] (issue [#51]).

[#51]: https://github.com/kotools/types/issues/51
[kotlin 1.6.21]: https://github.com/JetBrains/kotlin/releases/tag/v1.6.21
[kotlinx.serialization 1.3.3]: https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.3.3

- Relocate the library from `io.github.kotools` to `org.kotools` (issue [#63]).
  Here's an example using the Kotlin DSL in Gradle:

```kotlin
// before
implementation("io.github.kotools:types:$version")
// after
implementation("org.kotools:types:$version")
```

[#63]: https://github.com/kotools/types/issues/63

### Deprecated

The collections declared as [data classes] will be converted to [classes] (or
[inline classes] when possible) in version [4.3.0], which means that their
`copy` function is deprecated and will be unavailable after their conversion
(issue [#97]). Here's an example for the `NotEmptyList` type:

```kotlin
val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
var y: NotEmptyList<Int>
// before
y = x.copy()
println(x == y) // true
// after
y = x.toList()
    .toNotEmptyList()
    .getOrThrow()
println(x == z) // true
```

[4.3.0]: https://github.com/kotools/types/milestone/4
[#97]: https://github.com/kotools/types/issues/97
[classes]: https://kotlinlang.org/docs/classes.html
[data classes]: https://kotlinlang.org/docs/data-classes.html
[inline classes]: https://kotlinlang.org/docs/inline-classes.html

### Security

- Sign all commits with an SSH key to attest the identity (issue [#82]).

[#82]: https://github.com/kotools/types/issues/82

- Remove internal usages of the
  [`SerialDescriptor`][kotlinx.serialization.SerialDescriptor] function from the
  [kotlinx.serialization]'s experimental API for the following types:
  `NotEmptyList`, `NotEmptySet` and `NotEmptyMap` (issue [#77]). Here's an
  example for the `NotEmptyList` type:

```kotlin
val elementSerializer: KSerializer<Int> = Int.serializer()
// before
val notEmptyListSerialName: String = NotEmptyList.serializer(elementSerializer)
    .descriptor
    .serialName
println(notEmptyListSerialName == "kotools.types.NotEmptyList") // true
// after
val notEmptyListSerialName: String = NotEmptyList.serializer(elementSerializer)
    .descriptor
    .serialName
val expectedSerialName: String = ListSerializer(elementSerializer)
    .descriptor
    .serialName
println(notEmptyListSerialName == expectedSerialName) // true
```

[kotlinx.serialization.SerialDescriptor]: https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-core/kotlinx.serialization.descriptors/-serial-descriptor.html
[kotlinx.serialization]: https://github.com/Kotlin/kotlinx.serialization
[#77]: https://github.com/kotools/types/issues/77

## 4.1.0

_Release date: 2023-04-03._

_See the [full changelog][4.0.1-4.1.0] from version [`4.0.1`][tag/4.0.1]._

[4.0.1-4.1.0]: https://github.com/kotools/types/compare/4.0.1...4.1.0
[tag/4.0.1]: https://github.com/kotools/types/releases/tag/4.0.1

### Added

- `NotEmptyCollection` hierarchy representing collections that contain at least
  one element (issue [#14]).

```kotlin
interface NotEmptyCollection<out E>
class NotEmptyList<out E> : NotEmptyCollection<E>
class NotEmptySet<out E> : NotEmptyCollection<E>
```

[#14]: https://github.com/kotools/types/issues/14

- Binary operations (`plus`, `minus`, `times`, `div` and `rem`) for the `AnyInt`
  hierarchy (issue [#31]).

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

[#31]: https://github.com/kotools/types/issues/31

- `resultOf` function for encapsulating computations of functions returning the
  [`Result`][kotlin.Result] type (issue [#37]).

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

[#37]: https://github.com/kotools/types/issues/37

### Changed

- Support for [Kotlin 1.5.32][kotlin-1.5.32] (issue [#6]).

[#6]: https://github.com/kotools/types/issues/6
[kotlin-1.5.32]: https://github.com/JetBrains/kotlin/releases/tag/v1.5.32

- The following builders now works on the [`Number`][kotlin.Number] type instead
  of the [`Int`][kotlin.Int] type only: `toNonZeroInt`, `toPositiveInt`,
  `toNegativeInt`, `toStrictlyPositiveInt` and `toStrictlyNegativeInt` (issue
  [#43]).

```kotlin
val x: Double = 0.1
var result: Result<StrictlyPositiveInt>
// before
result = x.toInt().toStrictlyPositiveInt()
// after
result = x.toStrictlyPositiveInt()
```

[#43]: https://github.com/kotools/types/issues/43
[kotlin.Int]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html
[kotlin.Number]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-number/index.html

- The `AnyInt` and the `NotBlankString` types are now inheriting from
  [`Comparable`][kotlin.Comparable] (issue [#45]).

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

[#45]: https://github.com/kotools/types/issues/45

## 4.0.1

_Release date: 2023-02-06._

_See the [full changelog][4.0.0-4.0.1] from version [`4.0.0`][tag/4.0.0]._

[4.0.0-4.0.1]: https://github.com/kotools/types/compare/4.0.0...4.0.1
[tag/4.0.0]: https://github.com/kotools/types/releases/tag/4.0.0

### Changed

Update documentation of declarations returning the [`Result`][kotlin.Result]
type (issue [#20]).

[#20]: https://github.com/kotools/types/issues/20
[kotlin.Result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html

### Fixed

Fix versioning annotation of declarations in the `kotools.types.number` package
(issue [#23]).

[#23]: https://github.com/kotools/types/issues/23

## 4.0.0

_Release date: 2023-01-03._

### Added

Introduce a new `AnyInt` hierarchy representing integers (issue
[#132 in kotools/libraries][kotools/libraries#132]).

```kotlin
interface AnyInt
interface NonZeroInt : AnyInt
interface PositiveInt : AnyInt
interface NegativeInt : AnyInt
class StrictlyPositiveInt : NonZeroInt, PositiveInt
class StrictlyNegativeInt : NonZeroInt, NegativeInt
object ZeroInt : PositiveInt, NegativeInt
```

[kotools/libraries#132]: https://github.com/kotools/libraries/issues/132

### Changed

- Move the `NotBlankString` type from the `kotools.types` package to the
  `kotools.types.text` package with a new API (issue
  [#133 in kotools/libraries][kotools/libraries#133]).

[kotools/libraries#133]: https://github.com/kotools/libraries/issues/133

- Move the following types from the `kotools.types` package to the
  `kotools.types.collection` package with a new API: `NotEmptyList`,
  `NotEmptySet` and `NotEmptyMap` (issue
  [#138 in kotools/libraries][kotools/libraries#138]).
  Examples are available in the `Removed` section below.

[kotools/libraries#138]: https://github.com/kotools/libraries/issues/138

### Removed

- Remove inheritance between the `NotBlankString` and the
  [`Comparable`][kotlin.Comparable] types (issue [#16]).

```kotlin
val text: NotBlankString = "hello world".toNotBlankString().getOrThrow()
text as Comparable<NotBlankString> // before
"$text" as Comparable<String> // after
```

[#16]: https://github.com/kotools/types/issues/16
[kotlin.Comparable]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable

- Remove inheritance between the `NotEmptyList` and the
  [`List`][kotlin.collections.List] types (issue [#8]).

```kotlin
val elements: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
elements as List<Int> // before
elements.toList() // after
```

[#8]: https://github.com/kotools/types/issues/8
[kotlin.collections.List]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/#kotlin.collections.List

- Remove inheritance between the `NotEmptySet` and the
  [`Set`][kotlin.collections.Set] types (issue [#9]).

```kotlin
val elements: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
elements as Set<Int> // before
elements.toSet() // after
```

[#9]: https://github.com/kotools/types/issues/9
[kotlin.collections.Set]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/#kotlin.collections.Set

- Remove inheritance between the `NotEmptyMap` and the
  [`Map`][kotlin.collections.Map] types (issue [#10]).

```kotlin
val entries: NotEmptyMap<String, Int> = notEmptyMapOf("a" to 1, "b" to 2)
entries as Map<String, Int> // before
entries.toMap() // after
```

[#10]: https://github.com/kotools/types/issues/10
[kotlin.collections.Map]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/#kotlin.collections.Map

- Remove all declarations from previous API while keeping the essentials: the
  types and their builder (issue
  [#37 in kotools/libraries][kotools/libraries#37]).

[kotools/libraries#37]: https://github.com/kotools/libraries/issues/37
