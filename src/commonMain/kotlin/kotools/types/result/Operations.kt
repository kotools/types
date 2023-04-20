package kotools.types.result

import kotools.types.SinceKotoolsTypes

/**
 * Transforms the encapsulated value from [T] to [R] if this result is a
 * [success][Result.isSuccess], or returns the original exception if this result
 * or the one returned by the [transform] function is a
 * [failure][Result.isFailure].
 */
@SinceKotoolsTypes("4.2")
public inline fun <T, R> Result<T>.flatMap(
    transform: (T) -> Result<R>
): Result<R> = exceptionOrNull()
    ?.let(Result.Companion::failure)
    ?: transform(getOrThrow())
