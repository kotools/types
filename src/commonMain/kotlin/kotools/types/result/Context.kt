package kotools.types.result

import kotools.types.SinceKotoolsTypes
import kotools.types.collection.NotEmptyList
import kotools.types.number.*
import kotools.types.text.NotBlankString
import kotools.types.collection.toNotEmptyList as delegateToNotEmptyList
import kotools.types.number.toNegativeInt as delegateToNegativeInt
import kotools.types.number.toNonZeroInt as delegateToNonZeroInt
import kotools.types.number.toPositiveInt as delegateToPositiveInt
import kotools.types.number.toStrictlyNegativeInt as delegateToStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt as delegateToStrictlyPositiveInt
import kotools.types.text.toNotBlankString as delegateToNotBlankString

/** Context available when calling the [resultOf] function. */
@SinceKotoolsTypes("4.1")
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
}

private object ResultContextImplementation : ResultContext

/**
 * Returns an encapsulated result of calling the [block] function in the
 * [ResultContext], or returns an encapsulated [Throwable] if calling the
 * [block] function throws an exception.
 */
@SinceKotoolsTypes("4.1")
public inline fun <T> resultOf(block: ResultContext.() -> T): Result<T> =
    ResultContextImplementation.runCatching(block)
