package kotools.types.result

import kotools.types.SinceKotoolsTypes

/** Context available when calling the [resultOf] function. */
@SinceKotoolsTypes("4.1")
public sealed interface ResultContext

private object ResultContextImplementation : ResultContext

/**
 * Returns an encapsulated result of calling the [block] function in the
 * [ResultContext], or returns an encapsulated [Throwable] if calling the
 * [block] function throws an exception.
 */
@SinceKotoolsTypes("4.1")
public inline fun <T> resultOf(block: ResultContext.() -> T): Result<T> =
    ResultContextImplementation.runCatching(block)
