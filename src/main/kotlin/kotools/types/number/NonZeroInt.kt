package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a non-zero int, or throws an [IllegalArgumentException]
 * if this value equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a non-zero int, or returns `null` if this value equals
 * `0`.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull this

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.plus(other: NonZeroInt): Int = this + other.value

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.minus(other: NonZeroInt): Int =
    this - other.value

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.times(other: NonZeroInt): Int = this * other.value

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: NonZeroInt): Int = this / other.value

/**
 * Represents integers that don't equal `0`.
 *
 * @constructor Returns the [value] as a non-zero int, or throws an
 * [IllegalArgumentException] if the [value] equals `0`.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NonZeroInt
@Throws(IllegalArgumentException::class)
public constructor(public val value: Int) : Comparable<NonZeroInt> {
    init {
        require(value != 0) { "Given value shouldn't equal 0." }
    }

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: NonZeroInt): Int =
        value.compareTo(other.value)

    /**
     * Returns this [value] as a strictly negative int, or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a strictly negative int, or returns `null` if
     * this [value] is strictly positive.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeInt orNull value

    override fun toString(): String = value.toString()

    /**
     * Returns this [value] as a positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /**
     * Returns this [value] as a positive int, or returns `null` if this [value]
     * is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull value

    /**
     * Returns this [value] as a strictly positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        StrictlyPositiveInt(value)

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] is strictly negative.
     */
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        StrictlyPositiveInt orNull value

    /**
     * Returns this [value] as a negative int, or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNegativeInt(): NegativeInt = NegativeInt(value)

    /**
     * Returns this [value] as a negative int, or returns `null` if this [value]
     * is strictly positive.
     */
    public fun toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int = value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyPositiveInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NegativeInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyNegativeInt): Int =
        value + other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyPositiveInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyNegativeInt): Int =
        value - other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): Int =
        value * other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): Int =
        value * other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: NonZeroInt): Int = value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): Int = value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): Int =
        value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): Int = value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): Int =
        value / other.value

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] equals `-1`, it returns `1` instead.
     * If this [value] is the [maximum][NonZeroInt.max], it returns the
     * [minimum][NonZeroInt.min] value instead.
     */
    public operator fun inc(): NonZeroInt = when (value) {
        -1 -> NonZeroInt(1)
        max.value -> min
        else -> NonZeroInt(value + 1)
    }

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    public operator fun dec(): NonZeroInt = when (value) {
        1 -> NonZeroInt(-1)
        min.value -> max
        else -> NonZeroInt(value - 1)
    }

    /** Returns this [value]. */
    public operator fun unaryPlus(): NonZeroInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    public companion object {
        /** The minimum value of a non-zero int. */
        public val min: NonZeroInt = NonZeroInt(Int.MIN_VALUE)

        /** The maximum value of a non-zero int. */
        public val max: NonZeroInt = NonZeroInt(Int.MAX_VALUE)

        /**
         * Returns the [value] as a non-zero int, or returns `null` if the
         * [value] equals `0`.
         */
        public infix fun orNull(value: Int): NonZeroInt? = try {
            NonZeroInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
