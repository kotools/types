package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

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

// ---------- Comparisons ----------

/**
 * Compares this object with the specified object for order.
 * Returns zero if this object is equal to the specified [other] object, a
 * negative number if it's less than [other], or a positive number if it's
 * greater than [other].
 */
@SinceKotoolsTypes("1.3")
public infix operator fun Int.compareTo(other: NonZeroInt): Int =
    compareTo(other.value)

// ---------- Conversions ----------

/**
 * Returns this value as a non-zero int, or throws an [IllegalArgumentException]
 * if this value equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/** Returns this value as a non-zero int or `null` if this value equals `0`. */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull this

/**
 * Represents integers that can't equal `0`.
 *
 * @constructor Returns the [value] as a non-zero int, or throws an
 * [IllegalArgumentException] if this [value] equals `0`.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NonZeroInt(public val value: Int) : Comparable<NonZeroInt> {
    init {
        require(value != 0) { "Given value shouldn't equal 0." }
    }

    // ---------- Unary operations ----------

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
    public infix operator fun minus(other: NegativeInt): Int =
        minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyNegativeInt): Int =
        minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): Int =
        times(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        NonZeroInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): Int =
        times(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        NonZeroInt(times(other.value))

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
    public infix operator fun div(other: PositiveInt): Int = div(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): Int =
        div(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): Int = div(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): Int =
        div(other.value)

    // ---------- Comparisons ----------

    /**
     * Compares this object with the specified object for order.
     * Returns zero if this object is equal to the specified [other] object, a
     * negative number if it's less than [other], or a positive number if it's
     * greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    override infix operator fun compareTo(other: NonZeroInt): Int =
        compareTo(other.value)

    /**
     * Compares this object with the specified object for order.
     * Returns zero if this object is equal to the specified [other] object, a
     * negative number if it's less than [other], or a positive number if it's
     * greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: PositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this object with the specified object for order.
     * Returns zero if this object is equal to the specified [other] object, a
     * negative number if it's less than [other], or a positive number if it's
     * greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyPositiveInt): Int =
        compareTo(other.value)

    /**
     * Compares this object with the specified object for order.
     * Returns zero if this object is equal to the specified [other] object, a
     * negative number if it's less than [other], or a positive number if it's
     * greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: NegativeInt): Int =
        compareTo(other.value)

    /**
     * Compares this object with the specified object for order.
     * Returns zero if this object is equal to the specified [other] object, a
     * negative number if it's less than [other], or a positive number if it's
     * greater than [other].
     */
    @SinceKotoolsTypes("1.3")
    public infix operator fun compareTo(other: StrictlyNegativeInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

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

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or throws an
     * [IllegalArgumentException] if it's strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        StrictlyPositiveInt(value)

    /**
     * Returns this [value] as a [StrictlyPositiveInt] or `null` if it's
     * strictly negative.
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
     * Returns this [value] as a negative int or `null` if this [value] is
     * strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull value

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or throws an
     * [IllegalArgumentException] if it's strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a [StrictlyNegativeInt] or `null` if it's
     * strictly positive.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeInt orNull value

    override fun toString(): String = value.toString()

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())

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
