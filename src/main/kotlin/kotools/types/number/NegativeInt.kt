package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

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
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: NegativeInt): Int = this / other.value

// ---------- Conversions ----------

/**
 * Returns this value as a negative int, or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a negative int or `null` if this value is strictly
 * positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull this

/**
 * Represents negative integers (including 0).
 *
 * @constructor Returns the [value] as a negative int, or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NegativeInt(
    public val value: Int
) : Comparable<NegativeInt> {
    init {
        require(value <= 0) { "Given value shouldn't be strictly positive." }
    }

    // ---------- Unary operations ----------

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

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyPositiveInt): Int =
        plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NegativeInt): NegativeInt =
        NegativeInt(plus(other.value))

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(
        other: StrictlyNegativeInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(plus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int = minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): NegativeInt =
        NegativeInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyPositiveInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): Int =
        minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyNegativeInt): Int =
        minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): Int = times(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(times(other.value))

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
    public infix operator fun div(other: PositiveInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(div(other.value))

    // ---------- Comparisons ----------

    /**
     * Compares this negative int with [other] for order.
     * Returns `0` if this negative int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    /**
     * Compares this negative int with [other] for order.
     * Returns `0` if this negative int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NonZeroInt): Int =
        compareTo(other.value)

    /**
     * Compares this negative int with [other] for order.
     * Returns `0` if this negative int equals [other], or a positive number if
     * it's greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: PositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this negative int with [other] and returns a negative number for
     * order.
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyPositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this negative int with [other] for order.
     * Returns `0` if this negative int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
     */
    override infix fun compareTo(other: NegativeInt): Int =
        compareTo(other.value)

    /**
     * Compares this negative int with [other] for order.
     * Returns `0` if this negative int equals [other], a negative number if
     * it's less than [other], or a positive number if it's greater than
     * [other].
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
     * Returns this [value] as a non-zero int or `null` if this [value] equals
     * `0`.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull value

    /**
     * Returns this [value] as a strictly negative int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a strictly negative int or `null` if this [value]
     * equals `0`.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeInt orNull value

    /**
     * Returns this [value] as a positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /**
     * Returns this [value] as a positive int or `null` if this [value] is
     * strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull value

    override fun toString(): String = value.toString()

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())

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
