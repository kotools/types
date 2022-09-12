package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.plus(other: KotoolsInt): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.minus(other: KotoolsInt): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.times(other: KotoolsInt): Int = times(other.value)

/*
TODO: This function doesn't throw an error when dividing a number by 0 on
 the Kotlin/JS platform.
 */
/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public infix operator fun Int.div(other: KotoolsInt): Int = div(other.value)

// ---------- Comparisons ----------

/**
 * Compares this value with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.compareTo(other: KotoolsInt): Int =
    compareTo(other.value)

/** Parent of every integer's representation in this library. */
@SinceKotoolsTypes("3.0")
public interface KotoolsInt : Comparable<Int> {
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
    public infix operator fun times(other: KotoolsInt): Int = times(other.value)

    /*
    TODO: This function doesn't throw an error when dividing a number by 0 on
     the Kotlin/JS platform.
     */
    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /*
    TODO: This function doesn't throw an error when dividing a number by 0 on
     the Kotlin/JS platform.
     */
    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: KotoolsInt): Int = div(other.value)

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
}
