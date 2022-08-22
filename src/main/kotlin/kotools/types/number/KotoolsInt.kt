package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("2.1")
public infix operator fun Int.plus(other: KotoolsInt): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("2.1")
public infix operator fun Int.minus(other: KotoolsInt): Int =
    minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("2.1")
public infix operator fun Int.times(other: KotoolsInt): Int =
    times(other.value)

// ---------- Comparisons ----------

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

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: KotoolsInt): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: KotoolsInt): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: KotoolsInt): Int =
        times(other.value)

    // ---------- Comparisons ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: Int): Int = value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: KotoolsInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this [value] as a not blank string.
     */
    public fun toNotBlankString(): NotBlankString {
        val string: String = value.toString()
        return NotBlankString(string)
    }
}
