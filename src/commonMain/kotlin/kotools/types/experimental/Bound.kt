package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Represents a bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
public sealed interface Bound<out T : Comparable<@UnsafeVariance T>> {
    /** The value of this bound. */
    public val value: T

    /** Returns the string representation of this bound's [value]. */
    override fun toString(): String
}

/**
 * Represents an inclusive bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
public class InclusiveBound<out T : Comparable<@UnsafeVariance T>>
private constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"

    /** Contains static declarations for the [InclusiveBound] type. */
    public companion object {
        @JvmSynthetic
        internal infix fun <T : Comparable<T>> of(value: T): InclusiveBound<T> =
            InclusiveBound(value)
    }
}

/**
 * Represents an exclusive bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
public class ExclusiveBound<out T : Comparable<@UnsafeVariance T>>
private constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"

    /** Contains static declarations for the [ExclusiveBound] type. */
    public companion object {
        @JvmSynthetic
        internal infix fun <T : Comparable<T>> of(value: T): ExclusiveBound<T> =
            ExclusiveBound(value)
    }
}
