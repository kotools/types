package kotools.types.result

import kotools.types.SinceKotoolsTypes
import kotools.types.number.NonZeroInt
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.ZeroInt
import kotools.types.text.NotBlankString
import kotools.types.number.toNonZeroInt as delegateToNonZeroInt
import kotools.types.number.toPositiveInt as delegateToPositiveInt
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
     * Returns this string as a [NotBlankString], or throws an
     * [IllegalArgumentException] if this string is [blank][String.isBlank].
     */
    @Throws(IllegalArgumentException::class)
    public fun String.toNotBlankString(): NotBlankString =
        delegateToNotBlankString()
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
