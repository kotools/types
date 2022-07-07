package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun NonZeroInt.toNegativeInt(): NegativeInt = NegativeInt(value)

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun PositiveInt.toNegativeInt(): NegativeInt = NegativeInt(value)

/**
 * Returns this value as a [NegativeInt] or `null` if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull this

/**
 * Returns this value as a [NegativeInt] or `null` if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun NonZeroInt.toNegativeIntOrNull(): NegativeInt? =
    NegativeInt orNull value

/**
 * Returns this value as a [NegativeInt] or `null` if it's strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun PositiveInt.toNegativeIntOrNull(): NegativeInt? =
    NegativeInt orNull value

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.plus(other: NegativeInt): Int =
    this + other.value

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.minus(other: NegativeInt): Int =
    this - other.value

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.times(other: NegativeInt): Int =
    this * other.value

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: NegativeInt): Int = this / other.value

/**
 * Represents negative integers (including 0).
 *
 * @constructor Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if it's strictly positive.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NegativeInt(
    public val value: Int
) : Comparable<NegativeInt> {
    init {
        require(value <= 0) { "Given value shouldn't be strictly positive." }
    }

    override fun compareTo(other: NegativeInt): Int =
        value.compareTo(other.value)

    /**
     * Returns this [value] as a [NonZeroInt], or throws an
     * [IllegalArgumentException] if it equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a [NonZeroInt] or `null` if it equals `0`. */
    public fun toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull value

    /**
     * Returns this [value] as a [PositiveInt], or throws an
     * [IllegalArgumentException] if it's strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /**
     * Returns this [value] as a [PositiveInt] or `null` if it's strictly
     * negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull value

    override fun toString(): String = value.toString()

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyPositiveInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NegativeInt): NegativeInt =
        NegativeInt(value + other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int =
        value - other.value

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): NegativeInt =
        NegativeInt(value - other.value)

    /**
     * Subtracts the [other] value from this [value].
     *
     * TODO: This method will return a StrictlyNegativeInt in
     *  [this issue](https://github.com/kotools/types/issues/19).
     */
    public infix operator fun minus(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value - other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): Int =
        value - other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): Int =
        value * other.value

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): NegativeInt =
        NegativeInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): PositiveInt =
        PositiveInt(value * other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: NonZeroInt): Int = value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][NegativeInt.max], it returns the
     * [minimum][NegativeInt.min] value instead.
     */
    public operator fun inc(): NegativeInt = if (value == max.value) min
    else NegativeInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][NegativeInt.min], it returns the
     * [maximum][NegativeInt.max] value instead.
     */
    public operator fun dec(): NegativeInt = if (value == min.value) max
    else NegativeInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): NegativeInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): PositiveInt = PositiveInt(-value)

    public companion object {
        /** The minimum value an instance of [NegativeInt] can have. */
        public val min: NegativeInt = NegativeInt(Int.MIN_VALUE)

        /** The maximum value an instance of [NegativeInt] can have. */
        public val max: NegativeInt = NegativeInt(0)

        /**
         * Returns the [value] as a [NegativeInt] or `null` if it's strictly
         * positive.
         */
        public infix fun orNull(value: Int): NegativeInt? = try {
            NegativeInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
