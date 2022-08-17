package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Compares this value with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("2.1")
public infix operator fun Int.compareTo(other: KotoolsInt): Int =
    compareTo(other.value)

/** Parent of every integer's representation in this library. */
@SinceKotoolsTypes("2.1")
public sealed interface KotoolsInt : Comparable<Int> {
    public val value: Int

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: Int): Int = value.compareTo(other)
}
