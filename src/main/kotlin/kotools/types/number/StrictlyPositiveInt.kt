package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.plus(other: StrictlyPositiveInt): Int =
    this + other.value

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.minus(other: StrictlyPositiveInt): Int =
    this - other.value

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.times(other: StrictlyPositiveInt): Int =
    this * other.value

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: StrictlyPositiveInt): Int =
    this / other.value

// ---------- Conversions ----------

/**
 * Returns this value as a strictly positive int, or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

/**
 * Represents strictly positive integers, excluding `0`.
 *
 * @constructor Returns the [value] as a strictly positive int, or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
@Throws(IllegalArgumentException::class)
public constructor(public val value: Int) : Comparable<StrictlyPositiveInt> {
    init {
        require(value > 0) { "Given value shouldn't be negative." }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][StrictlyPositiveInt.max], it returns the
     * [minimum][StrictlyPositiveInt.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else StrictlyPositiveInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][StrictlyPositiveInt.min], it returns the
     * [maximum][StrictlyPositiveInt.max] value instead.
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else StrictlyPositiveInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): StrictlyPositiveInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyNegativeInt =
        StrictlyNegativeInt(-value)

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): StrictlyPositiveInt =
        StrictlyPositiveInt(plus(other.value))

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NegativeInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyNegativeInt): Int =
        plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int = minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): Int =
        minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyPositiveInt): Int =
        minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): StrictlyPositiveInt =
        StrictlyPositiveInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyNegativeInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(minus(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyPositiveInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyNegativeInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(times(other.value))

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
    public infix operator fun div(other: NonZeroInt): Int = div(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    // ---------- Comparisons ----------

    /**
     * Compares this strictly positive int with [other] for order.
     * Returns `0` if this strictly positive int equals [other], a negative
     * number if it's less than [other], or a positive number if it's greater
     * than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    /**
     * Compares this strictly positive int with [other] for order.
     * Returns `0` if this strictly positive int equals [other], a negative
     * number if it's less than [other], or a positive number if it's greater
     * than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NonZeroInt): Int =
        compareTo(other.value)

    /**
     * Compares this strictly positive int with [other] for order.
     * Returns `0` if this strictly positive int equals [other], a negative
     * number if it's less than [other], or a positive number if it's greater
     * than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: PositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: StrictlyPositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this strictly positive int with [other] and returns a positive
     * number for order.
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NegativeInt): Int =
        compareTo(other.value)

    /**
     * Compares this strictly positive int with [other] and returns a positive
     * number for order.
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyNegativeInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /** Returns this [value] as a non-zero int. */
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a positive int. */
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    override fun toString(): String = value.toString()

    /**
     * Returns the string representation of this [value] as a not blank string.
     */
    @SinceKotoolsTypes("1.2")
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())

    public companion object {
        /** The minimum value of a strictly positive int. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(1)

        /** The maximum value of a strictly positive int. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(Int.MAX_VALUE)

        /**
         * Returns the [value] as a strictly positive int, or returns `null` if
         * the [value] is negative.
         */
        public infix fun orNull(value: Int): StrictlyPositiveInt? = try {
            StrictlyPositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
