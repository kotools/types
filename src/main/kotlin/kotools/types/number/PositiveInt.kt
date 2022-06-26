package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if it's strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if it's strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun NonZeroInt.toPositiveInt(): PositiveInt = PositiveInt(value)

/**
 * Returns this value as a [PositiveInt] or `null` if it's strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

/**
 * Returns this value as a [PositiveInt] or `null` if it's strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun NonZeroInt.toPositiveIntOrNull(): PositiveInt? =
    PositiveInt orNull value

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.plus(other: PositiveInt): Int =
    this + other.value

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.minus(other: PositiveInt): Int =
    this - other.value

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.times(other: PositiveInt): Int =
    this * other.value

/**
 * Divides this value by [other], truncating the result to an integer that is
 * closer to zero.
 * Throws an [ArithmeticException] if [other] equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(ArithmeticException::class)
public infix operator fun Int.div(other: PositiveInt): Int = this / other.value

/**
 * Represents positive integers (including `0`).
 *
 * @constructor Returns the [value] as a [PositiveInt], or throws an
 * [IllegalArgumentException] if it's strictly negative.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class PositiveInt(public val value: Int) :
    Comparable<PositiveInt> {
    init {
        require(value >= 0) { "Given value shouldn't be strictly negative." }
    }

    override fun compareTo(other: PositiveInt): Int =
        value.compareTo(other.value)

    override fun toString(): String = value.toString()

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): PositiveInt =
        PositiveInt(value + other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): Int =
        value - other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): Int =
        value * other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): PositiveInt =
        PositiveInt(value * other.value)

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to zero.
     * Throws an [ArithmeticException] if [other] equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to zero.
     */
    public infix operator fun div(other: NonZeroInt): Int = value / other.value

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to zero.
     * Throws an [ArithmeticException] if other equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][NonZeroInt.max], it returns the
     * [minimum][NonZeroInt.min] value instead.
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else PositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else PositiveInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): PositiveInt = this

    // TODO: This function will return a NegativeInt (see issue #15).
    /** Return the negative of this [value]. */
    public operator fun unaryMinus(): Int = -value

    public companion object {
        /** The minimum value an instance of [PositiveInt] can have. */
        public val min: PositiveInt = PositiveInt(0)

        /** The maximum value an instance of [PositiveInt] can have. */
        public val max: PositiveInt = PositiveInt(Int.MAX_VALUE)

        /**
         * Returns the [value] as a [PositiveInt] or `null` if it's strictly
         * negative.
         */
        public infix fun orNull(value: Int): PositiveInt? = try {
            PositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
