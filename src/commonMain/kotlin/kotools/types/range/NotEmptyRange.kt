package kotools.types.range

import kotools.types.SinceKotoolsTypes

/**
 * Representation of a range of comparable values that contain at least one
 * value.
 */
@SinceKotoolsTypes("4.2")
public data class NotEmptyRange<T : Comparable<T>> internal constructor(
    /** The start of this range. */
    public val start: Bound<T>,
    /** The end of this range. */
    public val end: Bound<T>
) {
    /**
     * Returns `true` if this range contains the given [value], or returns
     * `false` otherwise.
     */
    public infix operator fun contains(value: T): Boolean {
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

    /** Returns the string representation of this range. */
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
