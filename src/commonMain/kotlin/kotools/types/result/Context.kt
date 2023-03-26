package kotools.types.result

import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString as delegateToNotBlankString

/** Context available when calling the [resultOf] function. */
@SinceKotoolsTypes("4.1")
public sealed interface ResultContext {
    /**
     * Returns this string as a [NotBlankString], or throws an
     * [IllegalArgumentException] if this string is [blank][String.isBlank].
     */
    @Throws(IllegalArgumentException::class)
    public fun String.toNotBlankString(): NotBlankString
}

private object ResultContextImplementation : ResultContext {
    override fun String.toNotBlankString(): NotBlankString =
        delegateToNotBlankString()
            .getOrThrow()
}

/**
 * Returns an encapsulated result of calling the [block] function in the
 * [ResultContext], or returns an encapsulated [Throwable] if calling the
 * [block] function throws an exception.
 */
@SinceKotoolsTypes("4.1")
public inline fun <T> resultOf(block: ResultContext.() -> T): Result<T> =
    ResultContextImplementation.runCatching(block)
