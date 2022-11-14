package kotools.types

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankStringOrThrow

// ---------- Result ----------

/**
 * Returns the [value][KotoolsTypesBuilderSuccess.value] of type [T] if this
 * result is a [success][KotoolsTypesBuilderSuccess], or returns the result of
 * calling the [action] function if this result is an
 * [error][KotoolsTypesBuilderError].
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public inline fun <T> KotoolsTypesBuilderResult<T>.onError(
    action: (KotoolsTypesBuilderError) -> T
): T = when (this) {
    is KotoolsTypesBuilderError -> action(this)
    is KotoolsTypesBuilderSuccess -> value
}

/**
 * Representation of builders result in Kotools Types.
 *
 * @param T The type of the successful result.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public sealed interface KotoolsTypesBuilderResult<out T>

// ---------- Error ----------

internal inline fun builderError(
    message: () -> NotBlankString
): KotoolsTypesBuilderError {
    val msg: NotBlankString = message()
    return KotoolsTypesBuilderError(msg)
}

internal inline fun <A> A.shouldBe(
    description: () -> NotBlankString
): KotoolsTypesBuilderError = builderError {
    "Given value should be ${description()} (tried with $this)."
        .toNotBlankStringOrThrow()
}

/** Error returned when the value for building a type is invalid. */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public class KotoolsTypesBuilderError
internal constructor(message: NotBlankString) :
    IllegalArgumentException(message.value),
    KotoolsTypesBuilderResult<Nothing>

// ---------- Success ----------

internal inline fun <A, B> A.toSuccessfulResult(
    lazyValue: (A) -> B
): KotoolsTypesBuilderSuccess<B> {
    val value: B = lazyValue(this)
    return KotoolsTypesBuilderSuccess(value)
}

/** Object returned when building a type is successful. */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public data class KotoolsTypesBuilderSuccess<out T>
internal constructor(val value: T) : KotoolsTypesBuilderResult<T>
