package kotools.types.number

import kotools.types.KotoolsTypesBuilderResult
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotools.types.onError

/**
 * Returns the [value] as a type [T], or returns `null` if the [value] is
 * invalid.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public infix fun <T : IntHolder> IntBuilder<T>.intOrNull(value: Int): T? =
    int(value)
        .onError { return null }

/**
 * Returns the [value] as a type [T], or throws an [IllegalArgumentException]
 * if the [value] is invalid.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public infix fun <T : IntHolder> IntBuilder<T>.intOrThrow(value: Int): T =
    int(value)
        .onError { throw it }

/**
 * Context responsible for building an [IntHolder].
 *
 * @param T The explicit integer to build.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public fun interface IntBuilder<out T : IntHolder> {
    /** Returns the [value] as a type [T]. */
    public infix fun int(value: Int): KotoolsTypesBuilderResult<T>
}
