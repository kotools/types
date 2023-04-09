package kotools.types.range

import kotools.types.SinceKotoolsTypes

/** Representation of a bound in a [range][NotEmptyRange]. */
@SinceKotoolsTypes("4.2")
public sealed interface Bound<T : Comparable<T>> {
    /** The value of this bound. */
    public val value: T

    /** Returns the string representation of this bound's [value]. */
    override fun toString(): String
}

/** Representation of an inclusive bound in a [range][NotEmptyRange]. */
@SinceKotoolsTypes("4.2")
public data class InclusiveBound<T : Comparable<T>>
internal constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"
}

/** Returns this comparable value as an [InclusiveBound]. */
@SinceKotoolsTypes("4.2")
public fun <T : Comparable<T>> T.toInclusiveBound(): InclusiveBound<T> =
    InclusiveBound(this)

/** Representation of an exclusive bound in a [range][NotEmptyRange]. */
@SinceKotoolsTypes("4.2")
public data class ExclusiveBound<T : Comparable<T>>
internal constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"
}

/** Returns this comparable value as an [ExclusiveBound]. */
@SinceKotoolsTypes("4.2")
public fun <T : Comparable<T>> T.toExclusiveBound(): ExclusiveBound<T> =
    ExclusiveBound(this)
