package kotools.types.result

import kotools.types.SinceKotoolsTypes

/**
 * Transforms the encapsulated value from [A] to [B] if this result is a
 * [success][Result.isSuccess], or returns the original exception if this result
 * or the one returned by the [transform] function is a
 * [failure][Result.isFailure].
 */
@SinceKotoolsTypes("4.2")
public inline fun <A, B> Result<A>.flatMap(
    transform: (A) -> Result<B>
): Result<B> = exceptionOrNull()
    ?.let(Result.Companion::failure)
    ?: transform(getOrThrow())
