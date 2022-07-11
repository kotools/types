package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if it's negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/** Returns this value as a [StrictlyPositiveInt] or `null` if it's negative. */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

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
 * that is closer to zero.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: StrictlyPositiveInt): Int =
    this / other.value

/**
 * Represents strictly positive integers (excluding `0`).
 *
 * @constructor Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if it's negative.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt(
    public val value: Int
) : Comparable<StrictlyPositiveInt> {
    init {
        require(value > 0) { "Given value shouldn't be negative." }
    }

    override fun compareTo(other: StrictlyPositiveInt): Int =
        value.compareTo(other.value)

    override fun toString(): String = value.toString()

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())

    /** Returns this [value] as a [NonZeroInt]. */
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a [PositiveInt]. */
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int =
        value + other.value

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): StrictlyPositiveInt =
        StrictlyPositiveInt(value + other.value)

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
    public infix operator fun minus(other: NegativeInt): StrictlyPositiveInt =
        StrictlyPositiveInt(value - other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyNegativeInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(value - other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): PositiveInt =
        PositiveInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyPositiveInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): NegativeInt =
        NegativeInt(value * other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyNegativeInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(value * other.value)

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
    public infix operator fun div(other: PositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][StrictlyPositiveInt.max], it returns the
     * [minimum][StrictlyPositiveInt.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else StrictlyPositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
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

    public companion object {
        /** The minimum value an instance of [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(1)

        /** The maximum value an instance of [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(Int.MAX_VALUE)

        /**
         * Returns the [value] as a [StrictlyPositiveInt] or `null` if it's
         * negative.
         */
        public infix fun orNull(value: Int): StrictlyPositiveInt? = try {
            StrictlyPositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
