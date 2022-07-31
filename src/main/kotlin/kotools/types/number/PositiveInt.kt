package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

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
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(ArithmeticException::class)
public infix operator fun Int.div(other: PositiveInt): Int = this / other.value

// ---------- Comparisons ----------

/**
 * Compares this int with [other] for order.
 * Returns `0` if this int equals [other], a negative number if it's less than
 * [other], or a positive number if it's greater than [other].
 */
@SinceKotoolsTypes("1.3")
public infix operator fun Int.compareTo(other: PositiveInt): Int =
    compareTo(other.value)

// ---------- Conversions ----------

/**
 * Returns this value as a positive int, or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a positive int, or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

/**
 * Represents positive integers, including `0`.
 *
 * @constructor Returns the [value] as a positive int, or throws an
 * [IllegalArgumentException] if this [value] is strictly negative.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class PositiveInt(
    public val value: Int
) : Comparable<PositiveInt> {
    init {
        require(value >= 0) { "Given value shouldn't be strictly negative." }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][PositiveInt.max], it returns the
     * [minimum][PositiveInt.min] value instead.
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else PositiveInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][PositiveInt.min], it returns the
     * [maximum][PositiveInt.max] value instead.
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else PositiveInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): PositiveInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NegativeInt = NegativeInt(-value)

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): PositiveInt =
        PositiveInt(plus(other.value))

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(
        other: StrictlyPositiveInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(plus(other.value))

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
    public infix operator fun minus(other: NegativeInt): PositiveInt =
        PositiveInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyNegativeInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(minus(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): Int = times(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(times(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: NonZeroInt): Int = div(other.value)

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    // ---------- Comparisons ----------

    /**
     * Compares this positive int with [other] for order.
     * Returns `0` if this positive int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    /**
     * Compares this positive int with [other] for order.
     * Returns `0` if this positive int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NonZeroInt): Int =
        compareTo(other.value)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: PositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this positive int with [other] for order.
     * Returns `0` if this positive int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyPositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this positive int with [other] for order.
     * Returns `0` if this positive int equals [other], or a positive number if
     * it's greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NegativeInt): Int =
        compareTo(other.value)

    /**
     * Compares this positive int with [other] and returns a positive number for
     * order.
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyNegativeInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a non-zero int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /**
     * Returns this [value] as a non-zero int, or returns `null` if this [value]
     * equals `0`.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull value

    /**
     * Returns this [value] as a strictly positive int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        StrictlyPositiveInt(value)

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] equals `0`.
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
    @Throws(IllegalArgumentException::class)
    public fun toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull value

    override fun toString(): String = value.toString()

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    @SinceKotoolsTypes("1.2")
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())

    public companion object {
        /** The minimum value of a positive int. */
        public val min: PositiveInt = PositiveInt(0)

        /** The maximum value of a positive int. */
        public val max: PositiveInt = PositiveInt(Int.MAX_VALUE)

        /**
         * Returns the [value] as a positive int, or returns `null` if the
         * [value] is strictly negative.
         */
        public infix fun orNull(value: Int): PositiveInt? = try {
            PositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
