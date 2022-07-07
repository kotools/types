package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if it equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/** Returns this value as a [NonZeroInt] or `null` if it equals `0`. */
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
 * that is closer to zero.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: NonZeroInt): Int = this / other.value

/**
 * Represents integers that can't equal `0`.
 *
 * @constructor Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if it equals `0`.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NonZeroInt(public val value: Int) : Comparable<NonZeroInt> {
    init {
        require(value != 0) { "Given value shouldn't equal 0." }
    }

    override fun compareTo(other: NonZeroInt): Int =
        value.compareTo(other.value)

    override fun toString(): String = value.toString()

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
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull value

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

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): NonZeroInt =
        NonZeroInt(value / other)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): NonZeroInt =
        NonZeroInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NonZeroInt =
        NonZeroInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NonZeroInt =
        NonZeroInt(value / other.value)

    /**
     * Returns this [value] incremented by one.
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
     * Returns this [value] decremented by one.
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
        /** The minimum value an instance of [NonZeroInt] can have. */
        public val min: NonZeroInt = NonZeroInt(Int.MIN_VALUE)

        /** The maximum value an instance of [NonZeroInt] can have. */
        public val max: NonZeroInt = NonZeroInt(Int.MAX_VALUE)

        /** Returns the [value] as a [NonZeroInt] or `null` if it equals `0`. */
        public infix fun orNull(value: Int): NonZeroInt? = try {
            NonZeroInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
