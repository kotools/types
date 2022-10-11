package kotools.types

import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.minus(other: IntHolder): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.times(other: IntHolder): Int = times(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): IntHolder

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public operator fun compareTo(other: Int): Int = value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: IntHolder): Int = compareTo(other.value)

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: IntHolder): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: IntHolder): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: IntHolder): Int = times(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: NonZeroInt): Int = value / other.value
}

private sealed class AbstractIntHolder(isValid: () -> Boolean) : IntHolder {
    init {
        require(isValid())
    }

    override fun equals(other: Any?): Boolean =
        other is IntHolder && value == other.value

    override fun hashCode(): Int = 31 * value.hashCode()

    override fun toString(): String = value.toString()
}

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the [value] equals zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun NonZeroInt(value: Int): NonZeroInt = NonZeroIntImplementation(value)

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? =
    tryOrNull { NonZeroInt(value) }

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.div(other: NonZeroInt): Int = div(other.value)

/** Representation of integers other than zero. */
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroInt : IntHolder {
    override fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value * other.value)
}

private class NonZeroIntImplementation(override val value: Int) :
    AbstractIntHolder({ value != 0 }),
    NonZeroInt

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun PositiveInt(value: Int): PositiveInt =
    PositiveIntImplementation(value)

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    tryOrNull { PositiveInt(value) }

/** Representation of positive integers, including zero. */
@SinceKotoolsTypes("3.0")
public sealed interface PositiveInt : IntHolder {
    override fun unaryMinus(): NegativeInt = NegativeInt(-value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(value / other.value)
}

private class PositiveIntImplementation(override val value: Int) :
    AbstractIntHolder({ value >= 0 }),
    PositiveInt

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun StrictlyPositiveInt(value: Int): StrictlyPositiveInt =
    StrictlyPositiveIntImplementation(value)

/**
 * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
 * [value] is negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyPositiveIntOrNull(value: Int): StrictlyPositiveInt? =
    tryOrNull { StrictlyPositiveInt(value) }

/** Representation of strictly positive integers, excluding zero. */
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyPositiveInt : NonZeroInt,
    PositiveInt {
    override fun unaryMinus(): StrictlyNegativeInt = StrictlyNegativeInt(-value)
}

private class StrictlyPositiveIntImplementation(override val value: Int) :
    AbstractIntHolder({ value > 0 }),
    StrictlyPositiveInt

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun NegativeInt(value: Int): NegativeInt =
    NegativeIntImplementation(value)

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    tryOrNull { NegativeInt(value) }

/** Representation of negative integers, including zero. */
@SinceKotoolsTypes("3.0")
public sealed interface NegativeInt : IntHolder {
    override fun unaryMinus(): PositiveInt = PositiveInt(-value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(value / other.value)
}

private class NegativeIntImplementation(override val value: Int) :
    AbstractIntHolder({ value <= 0 }),
    NegativeInt

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    StrictlyNegativeIntImplementation(value)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    tryOrNull { StrictlyNegativeInt(value) }

/** Representation of strictly negative integers, excluding zero. */
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    override fun unaryMinus(): StrictlyPositiveInt = StrictlyPositiveInt(-value)
}

private class StrictlyNegativeIntImplementation(override val value: Int) :
    AbstractIntHolder({ value < 0 }),
    StrictlyNegativeInt
