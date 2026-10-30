# 🔥 Replace factory functions that return `Result`

> [!WARNING]
> This idea includes **incompatible source** and **binary** changes.
> See our [versioning strategy](../versioning-strategy.md) for more details on
> these types of compatibility.

## 🎯 Goal

In Kotools Types 5, some factory functions under the package `kotools.types`
return `Result`.

But [KEEP 0127] says that `Result` _"is not designed to represent
domain-specific error conditions"_. Instead, it recommends using nullable types,
domain-specific data types or exceptions instead.

The goal is to replace each of these `Result` functions by a pair of `*OrNull`
and `*OrThrow` functions.

> [!NOTE]
> **Deep-module check:** Alone, `*OrThrow` functions would be a pass-through of
> `toX().getOrThrow()`. It is accepted only because they replace the `Result`
> functions. `*OrNull` functions hide the same validation behind a nullable
> return.

> [!NOTE]
> The function [`toIntOrNull`][to-int-or-null] from the Kotlin standard
> library is a precedent for the `OrNull` suffix.

## 🧩 API

```kotlin
// Before:

public fun Number.toNonZeroInt(): Result<NonZeroInt>
public fun Number.toPositiveInt(): Result<PositiveInt>
public fun Number.toNegativeInt(): Result<NegativeInt>
public fun Number.toStrictlyPositiveInt(): Result<StrictlyPositiveInt>
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt>
public fun String.toNotBlankString(): Result<NotBlankString>
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>>
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>>
public fun <K, V> Map<K, V>.toNotEmptyMap(): Result<NotEmptyMap<K, V>>

// After:

public fun Number.toNonZeroIntOrNull(): NonZeroInt?
public fun Number.toNonZeroIntOrThrow(): NonZeroInt

public fun Number.toPositiveIntOrNull(): PositiveInt?
public fun Number.toPositiveIntOrThrow(): PositiveInt

public fun Number.toNegativeIntOrNull(): NegativeInt?
public fun Number.toNegativeIntOrThrow(): NegativeInt

public fun Number.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt?
public fun Number.toStrictlyPositiveIntOrThrow(): StrictlyPositiveInt

public fun Number.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt?
public fun Number.toStrictlyNegativeIntOrThrow(): StrictlyNegativeInt

public fun String.toNotBlankStringOrNull(): NotBlankString?
public fun String.toNotBlankStringOrThrow(): NotBlankString

public fun <E> Collection<E>.toNotEmptyListOrNull(): NotEmptyList<E>?
public fun <E> Collection<E>.toNotEmptyListOrThrow(): NotEmptyList<E>

public fun <E> Collection<E>.toNotEmptySetOrNull(): NotEmptySet<E>?
public fun <E> Collection<E>.toNotEmptySetOrThrow(): NotEmptySet<E>

public fun <K, V> Map<K, V>.toNotEmptyMapOrNull(): NotEmptyMap<K, V>?
public fun <K, V> Map<K, V>.toNotEmptyMapOrThrow(): NotEmptyMap<K, V>
```

## 📝 Specifications

> Each rule in this section is expressed using an [RFC 2119] keyword.

- New pairs of functions **MUST** live in the same file of the type it builds.
- New `*OrThrow` functions **MUST** throw an `IllegalArgumentException` in case
  of invalid input.
- New `*OrNull` functions **MUST** be hidden from Java code using
  `@JvmSynthetic`.
- The conversion of numbers **MUST** be unchanged: use `Number.toInt()` to
  truncate the fractional part and saturate out-of-range values, then check the
  range.
- Factory functions returning `Result` **MUST** be deprecated following our
  [Declarations lifecycle](../declarations-lifecycle.md).
- `resultOf` and `ResultContext` **MUST** be deprecated too, because `*OrThrow`
  functions make their throwing variants redundant.

## ✅ Issues

- ✨ Add `*OrNull` and `*OrThrow` functions to types under `kotools.types.*`
  ([#1060])
- 🗑️ Deprecate `Result` functions with warning ([#1061])
- 🗑️ Deprecate `Result` functions with error ([#1062])
- 🗑️ Hide `Result` functions ([#1063])
- 🔥 Remove `Result` functions ([#1064])

<!----------------------------------- Links ----------------------------------->

[#1060]: https://github.com/kotools/types/issues/1060
[#1061]: https://github.com/kotools/types/issues/1061
[#1062]: https://github.com/kotools/types/issues/1062
[#1063]: https://github.com/kotools/types/issues/1063
[#1064]: https://github.com/kotools/types/issues/1064
[KEEP 0127]: https://github.com/Kotlin/KEEP/blob/10ed91ea195b05a9388794db8a7e62f6afdf4717/proposals/stdlib/KEEP-0127-result.md
[RFC 2119]: https://datatracker.ietf.org/doc/html/rfc2119
[to-int-or-null]: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/to-int-or-null.html
