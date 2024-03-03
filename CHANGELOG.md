# 🔄 Changelog

All notable changes to this project will be documented in this file.

> The format of this document is based on [Keep a Changelog].

> See [the changelog in kotools/libraries] for versions `1`, `2` and `3`.

[keep a changelog]: https://keepachangelog.com/en/1.1.0
[the changelog in kotools/libraries]: https://github.com/kotools/libraries/blob/types-v3.2.0/types/changelog.md

## 🤔 Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## 🚧 Unreleased

### Added

- Support the macOS arm64 platform with Kotlin Native (issue
  [#414](https://github.com/kotools/types/issues/414)).
- The following **experimental** factory functions:
  - `create(Any?)` and `createOrNull(Any?)` in `NotBlankString.Companion` (issue
    [#341](https://github.com/kotools/types/issues/341))
  - `create(Number)` and `createOrNull(Number)` in
    `StrictlyPositiveInt.Companion` (issue
    [#342](https://github.com/kotools/types/issues/342)), in
    `StrictlyNegativeInt.Companion` (issue
    [#347](https://github.com/kotools/types/issues/347)), in
    `PositiveInt.Companion` (issue
    [#349](https://github.com/kotools/types/issues/349)), in
    `NegativeInt.Companion` (issue
    [#350](https://github.com/kotools/types/issues/350)), and in
    `NonZeroInt.Companion` (issue
    [#351](https://github.com/kotools/types/issues/351))
  - `create(Collection<E>)`, `createOrNull(Collection<E>)` and `of(E, vararg E)`
    in `NotEmptyList.Companion` (issue
    [#352](https://github.com/kotools/types/issues/352)) and in
    `NotEmptySet.Companion` (issue
    [#353](https://github.com/kotools/types/issues/353))
  - `create(Map<K, V>)`, `createOrNull(Map<K, V>)` and
    `of(Pair<K, V>, vararg Pair<K, V>)` in `NotEmptyMap.Companion` (issue
    [#354](https://github.com/kotools/types/issues/354)).
- Documentation of dependency compatibility (issue
  [#288](https://github.com/kotools/types/issues/288)).

### Changed

- Bump embedded Kotlin from 1.7.21 to 1.8.22 (issues
  [#172](https://github.com/kotools/types/issues/172) and
  [#196](https://github.com/kotools/types/issues/196)).
- Bump kotlinx.serialization from 1.4.0 to 1.5.1 (issues
  [#378](https://github.com/kotools/types/issues/378) and
  [#381](https://github.com/kotools/types/issues/381)).
- Move the `EmailAddress` **experimental** type from the
  `kotools.types.experimental` package to the new `kotools.types.web` one (issue
  [#377](https://github.com/kotools/types/issues/377)).
- Make the `regex` property of the `EmailAddress` **experimental** type
  inaccessible for Java sources, due to the unavailability of the
  `kotlin.text.Regex` type for this language (commit
  [8d0d098ad](https://github.com/kotools/types/commit/8d0d098ad)).
- Update the regular expression of the `EmailAddress` **experimental** type for
  following the
  [RFC-5322](https://datatracker.ietf.org/doc/html/rfc5322#section-3.4.1)
  (issue [#394](https://github.com/kotools/types/issues/394)).
- Update our Git commit messages convention using
  [Gitmoji](https://github.com/carloscuesta/gitmoji) in the contributing
  guidelines (issue [#490](https://github.com/kotools/types/issues/490)).

### Deprecated

Deprecation promotion of the following annotations to error (issue
[#333](https://github.com/kotools/types/issues/333)):
- `ExperimentalCollectionApi`
- `ExperimentalNumberApi`
- `ExperimentalRangeApi`
- `ExperimentalResultApi`
- `ExperimentalTextApi`

### Security

Upgrade follow-redirects to 1.15.4 on Kotlin/JS platform because prior versions
are vulnerable to Improper Input Validation due to the improper handling of URLs
by the `url.parse()` function (issue
[#375](https://github.com/kotools/types/issues/375)).
See the [security report](https://github.com/advisories/GHSA-jchw-25xp-jwwc) for
more details on this vulnerability.

## 🔖 Releases

- [4.4.2] - 2024-02-07
- [4.4.1] - 2024-02-02
- [4.4.0] - 2024-01-29
- [4.3.1] - 2023-09-25
- [4.3.0] - 2023-08-14
- [4.2.0] - 2023-06-24

[4.4.2]: https://github.com/kotools/types/releases/tag/4.4.2
[4.4.1]: https://github.com/kotools/types/releases/tag/4.4.1
[4.4.0]: https://github.com/kotools/types/releases/tag/4.4.0
[4.3.1]: https://github.com/kotools/types/releases/tag/4.3.1
[4.3.0]: https://github.com/kotools/types/releases/tag/4.3.0
[4.2.0]: https://github.com/kotools/types/releases/tag/4.2.0

---

## ✨ 4.1.0 <a id="4.1.0"></a>

_Release date: 2023-04-03._

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

## 🚑️ 4.0.1 <a id="4.0.1"></a>

_Release date: 2023-02-06._

### Changed

Update documentation of declarations returning the [`Result`][kotlin.Result]
type (issue [#20]).

[#20]: https://github.com/kotools/types/issues/20
[kotlin.Result]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/index.html

### Fixed

Fix versioning annotation of declarations in the `kotools.types.number` package
(issue [#23]).

[#23]: https://github.com/kotools/types/issues/23

## 💥 4.0.0 <a id="4.0.0"></a>

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

<!------------------------------- Shared links -------------------------------->

<!-- Starting with '#' character (code 35) -->
[#55]: https://github.com/kotools/types/discussions/55
[#104]: https://github.com/kotools/types/discussions/104
[#125]: https://github.com/kotools/types/issues/125
[#132]: https://github.com/kotools/types/issues/132
[#141]: https://github.com/kotools/types/issues/141
[#149]: https://github.com/kotools/types/issues/149
[#155]: https://github.com/kotools/types/issues/155
[#167]: https://github.com/kotools/types/pull/167
[#171]: https://github.com/kotools/types/issues/171
[#173]: https://github.com/kotools/types/issues/173
[#174]: https://github.com/kotools/types/issues/174
[#176]: https://github.com/kotools/types/issues/176
[#177]: https://github.com/kotools/types/issues/177
[#178]: https://github.com/kotools/types/issues/178
[#179]: https://github.com/kotools/types/issues/179
[#191]: https://github.com/kotools/types/issues/191
[#198]: https://github.com/kotools/types/pull/198
[#199]: https://github.com/kotools/types/pull/199
[#205]: https://github.com/kotools/types/issues/205
[#207]: https://github.com/kotools/types/issues/207
[#213]: https://github.com/kotools/types/pull/213
[#215]: https://github.com/kotools/types/issues/215
[#222]: https://github.com/kotools/types/pull/222
[#250]: https://github.com/kotools/types/issues/250
[#257]: https://github.com/kotools/types/issues/257
[#258]: https://github.com/kotools/types/issues/258
[#261]: https://github.com/kotools/types/issues/261
[#303]: https://github.com/kotools/types/issues/303
[#307]: https://github.com/kotools/types/issues/307
[#313]: https://github.com/kotools/types/issues/313
[#319]: https://github.com/kotools/types/issues/319
[#327]: https://github.com/kotools/types/issues/327
[#328]: https://github.com/kotools/types/pull/328
[#339]: https://github.com/kotools/types/issues/339
[#406]: https://github.com/kotools/types/issues/406
[#431]: https://github.com/kotools/types/issues/431

<!-- Starting with '@' character (code 64) -->
[@MichaelStH]: https://github.com/MichaelStH
[@o-korpi]: https://github.com/o-korpi
[@robertfmurdock]: https://github.com/robertfmurdock

<!-- Starting with lowercase letter (code in [97;122]) -->
[api-reference]: https://types.kotools.org
[commit-a4399cce]: https://github.com/kotools/types/commit/a4399cce
[commit-a8aedb49]: https://github.com/kotools/types/commit/a8aedb49
[commit-ed9322d1]: https://github.com/kotools/types/commit/ed9322d1
[commit-557350b5]: https://github.com/kotools/types/commit/557350b5
[commit-571428bc]: https://github.com/kotools/types/commit/571428bc
[commit-5ef8aa05]: https://github.com/kotools/types/commit/5ef8aa05
[commit-76ba0637]: https://github.com/kotools/types/commit/76ba0637
[commit-79e093ce]: https://github.com/kotools/types/commit/79e093ce
[commit-9052e77d]: https://github.com/kotools/types/commit/9052e77d
[ssh-deploy-key]: https://docs.github.com/fr/authentication/connecting-to-github-with-ssh/managing-deploy-keys#deploy-keys
[tag-1.3.1]: https://github.com/kotools/types-legacy/releases/tag/v1.3.1
[tag-2.0.0]: https://github.com/kotools/types-legacy/releases/tag/v2.0.0
[tag-3.2.0]: https://github.com/kotools/libraries/releases/tag/types-v3.2.0
[tag-4.0.0]: https://github.com/kotools/types/releases/tag/4.0.0
[tag-4.0.1]: https://github.com/kotools/types/releases/tag/4.0.1
[tag-4.1.0]: https://github.com/kotools/types/releases/tag/4.1.0
[tag-4.2.0]: https://github.com/kotools/types/releases/tag/4.2.0
[webpack@v5.76.3]: https://github.com/webpack/webpack/releases/tag/v5.76.3
