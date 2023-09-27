package kotools.types.range

import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.experimental.ExperimentalRangeApi
import kotlin.jvm.JvmSynthetic

/**
 * Represents a bound in a [range][NotEmptyRange].
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalRangeApi
@ExperimentalSinceKotoolsTypes("4.2")
public sealed interface Bound<out T : Comparable<@UnsafeVariance T>> {
    /** The value of this bound. */
    public val value: T

    /** Returns the string representation of this bound's [value]. */
    override fun toString(): String
}

/**
 * Represents an inclusive bound in a [range][NotEmptyRange].
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalRangeApi
@ExperimentalSinceKotoolsTypes("4.2")
public class InclusiveBound<out T : Comparable<@UnsafeVariance T>>
private constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"

    internal companion object {
        @JvmSynthetic
        internal fun <T : Comparable<T>> of(value: T): InclusiveBound<T> =
            InclusiveBound(value)
    }
}

/**
 * Represents an exclusive bound in a [range][NotEmptyRange].
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalRangeApi
@ExperimentalSinceKotoolsTypes("4.2")
public class ExclusiveBound<out T : Comparable<@UnsafeVariance T>>
internal constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"
}
