package kotools.types.range

import kotools.types.SinceKotoolsTypes

/**
 * Representation of a range of comparable values that contain at least one
 * value.
 */
@SinceKotoolsTypes("4.2")
public sealed interface NotEmptyRange<T : Comparable<T>> {
    /** The start of this range. */
    public val start: Bound<T>

    /** The end of this range. */
    public val end: Bound<T>

    /** Returns the string representation of this range. */
    override fun toString(): String
}

private data class NotEmptyRangeImplementation<T : Comparable<T>>(
    override val start: Bound<T>,
    override val end: Bound<T>
) : NotEmptyRange<T> {
    override fun toString(): String {
        val prefix: Char = when (start) {
            is InclusiveBound -> '['
            is ExclusiveBound -> ']'
        }
        val suffix: Char = when (end) {
            is InclusiveBound -> ']'
            is ExclusiveBound -> '['
        }
        return "$prefix$start;$end$suffix"
    }
}

/**
 * Returns `true` if this range contains the given [value], or returns `false`
 * otherwise.
 */
@SinceKotoolsTypes("4.2")
public operator fun <T : Comparable<T>> NotEmptyRange<T>.contains(
    value: T
): Boolean {
    val valueIsGreaterThanStart: Boolean = when (start) {
        is InclusiveBound -> value >= start.value
        is ExclusiveBound -> value > start.value
    }
    val valueIsLowerThanEnd: Boolean = when (end) {
        is InclusiveBound -> value <= end.value
        is ExclusiveBound -> value < end.value
    }
    return valueIsGreaterThanStart && valueIsLowerThanEnd
}

/**
 * Creates a [NotEmptyRange] using this bound and the [other] one.
 * If the [other] bound is lower than this bound, the resulting range will
 * [start][NotEmptyRange.start] with the [other] bound.
 */
@SinceKotoolsTypes("4.2")
public operator fun <T : Comparable<T>> Bound<T>.rangeTo(
    other: Bound<T>
): NotEmptyRange<T> = if (value <= other.value) NotEmptyRangeImplementation(
    start = this,
    end = other
) else NotEmptyRangeImplementation(start = other, end = this)
