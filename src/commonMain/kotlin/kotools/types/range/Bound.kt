package kotools.types.range

import kotools.types.SinceKotoolsTypes

/** Representation of a bound in a [range][NotEmptyRange]. */
@SinceKotoolsTypes("4.2")
public sealed interface Bound<T : Comparable<T>> {
    /** The value of this bound. */
    public val value: T

    /**
     * Creates a [NotEmptyRange] using this bound and the [other] one.
     * If the [other] bound is lower than this bound, the resulting range will
     * [start][NotEmptyRange.start] with the [other] bound.
     */
    public infix operator fun rangeTo(other: Bound<T>): NotEmptyRange<T> =
        if (value <= other.value) NotEmptyRange(start = this, end = other)
        else NotEmptyRange(start = other, end = this)

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
