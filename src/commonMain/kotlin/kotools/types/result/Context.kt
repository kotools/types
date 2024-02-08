package kotools.types.result

import kotools.types.collection.NotEmptyList
import kotools.types.collection.NotEmptyMap
import kotools.types.collection.NotEmptySet
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.number.NegativeInt
import kotools.types.number.NonZeroInt
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.text.NotBlankString
import kotools.types.collection.toNotEmptyList as delegateToNotEmptyList
import kotools.types.collection.toNotEmptyMap as delegateToNotEmptyMap
import kotools.types.collection.toNotEmptySet as delegateToNotEmptySet
import kotools.types.number.toNegativeInt as delegateToNegativeInt
import kotools.types.number.toNonZeroInt as delegateToNonZeroInt
import kotools.types.number.toPositiveInt as delegateToPositiveInt
import kotools.types.number.toStrictlyNegativeInt as delegateToStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt as delegateToStrictlyPositiveInt
import kotools.types.text.toNotBlankString as delegateToNotBlankString

/** Context available when calling the [resultOf] function. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public sealed interface ResultContext {
    /**
     * Returns this number as a [NonZeroInt], which may involve rounding or
     * truncation, or throws an [IllegalArgumentException] if this number
     * equals [zero][ZeroInt].
     */
    @Throws(IllegalArgumentException::class)
    public fun Number.toNonZeroInt(): NonZeroInt = delegateToNonZeroInt()
        .getOrThrow()

    /**
     * Returns this number as a [PositiveInt], which may involve rounding or
     * truncation, or throws an [IllegalArgumentException] if this number is
     * [strictly negative][StrictlyNegativeInt].
     */
    @Throws(IllegalArgumentException::class)
    public fun Number.toPositiveInt(): PositiveInt = delegateToPositiveInt()
        .getOrThrow()

    /**
     * Returns this number as a [NegativeInt], which may involve rounding or
     * truncation, or throws an [IllegalArgumentException] if this number is
     * [strictly positive][StrictlyPositiveInt].
     */
    @Throws(IllegalArgumentException::class)
    public fun Number.toNegativeInt(): NegativeInt = delegateToNegativeInt()
        .getOrThrow()

    /**
     * Returns this number as a [StrictlyPositiveInt], which may involve
     * rounding or truncation, or throws an [IllegalArgumentException] if this
     * number is [negative][NegativeInt].
     */
    @Throws(IllegalArgumentException::class)
    public fun Number.toStrictlyPositiveInt(): StrictlyPositiveInt =
        delegateToStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Returns this number as a [StrictlyNegativeInt], which may involve
     * rounding or truncation, or throws an [IllegalArgumentException] if this
     * number is [positive][PositiveInt].
     */
    @Throws(IllegalArgumentException::class)
    public fun Number.toStrictlyNegativeInt(): StrictlyNegativeInt =
        delegateToStrictlyNegativeInt()
            .getOrThrow()

    /**
     * Returns this string as a [NotBlankString], or throws an
     * [IllegalArgumentException] if this string is [blank][String.isBlank].
     */
    @Throws(IllegalArgumentException::class)
    public fun String.toNotBlankString(): NotBlankString =
        delegateToNotBlankString()
            .getOrThrow()

    /**
     * Returns a [NotEmptyList] containing all the elements of this collection,
     * or throws an [IllegalArgumentException] if this collection is
     * [empty][Collection.isEmpty].
     */
    @Throws(IllegalArgumentException::class)
    public fun <E> Collection<E>.toNotEmptyList(): NotEmptyList<E> =
        delegateToNotEmptyList()
            .getOrThrow()

    /**
     * Returns a [NotEmptySet] containing all the elements of this collection,
     * or throws an [IllegalArgumentException] if this collection is
     * [empty][Collection.isEmpty].
     */
    @Throws(IllegalArgumentException::class)
    public fun <E> Collection<E>.toNotEmptySet(): NotEmptySet<E> =
        delegateToNotEmptySet()
            .getOrThrow()

    /**
     * Returns a [NotEmptyMap] containing all the entries of this map, or throws
     * an [IllegalArgumentException] if this map is [empty][Map.isEmpty].
     */
    @Throws(IllegalArgumentException::class)
    public fun <K, V> Map<K, V>.toNotEmptyMap(): NotEmptyMap<K, V> =
        delegateToNotEmptyMap()
            .getOrThrow()
}

private object ResultContextImplementation : ResultContext

/**
 * Returns an encapsulated result of calling the [block] function in the
 * [ResultContext], or returns an encapsulated [Throwable] if calling the
 * [block] function throws an exception.
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public inline fun <T> resultOf(block: ResultContext.() -> T): Result<T> =
    ResultContextImplementation.runCatching(block)
