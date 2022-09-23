package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

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

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a [StrictlyNegativeInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toInt().toStrictlyNegativeInt()

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    StrictlyNegativeIntOrNull(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * positive number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    toIntOrNull()?.toStrictlyNegativeIntOrNull()

/**
 * Parent of classes responsible for holding strictly negative integers,
 * excluding zero.
 */
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyNegativeInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][StrictlyNegativeInt.max], it returns
     * the [minimum][StrictlyNegativeInt.min] value instead.
     */
    public operator fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else StrictlyNegativeInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][StrictlyNegativeInt.min], it returns
     * the [maximum][StrictlyNegativeInt.max] value instead.
     */
    public operator fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else StrictlyNegativeInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyPositiveInt =
        StrictlyPositiveInt(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: PositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: NegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

    // ---------- Conversions ----------

    /** Returns this [value] as a [NonZeroInt]. */
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a [NegativeInt]. */
    public fun toNegativeInt(): NegativeInt = NegativeInt(value)

    public companion object {
        internal val range: IntRange = NegativeInt.min.value..-1

        /** The minimum value of a [StrictlyNegativeInt]. */
        public val min: StrictlyNegativeInt = StrictlyNegativeInt(range.first)

        /** The maximum value of a [StrictlyNegativeInt]. */
        public val max: StrictlyNegativeInt = StrictlyNegativeInt(range.last)

        /** Returns a random [StrictlyNegativeInt]. */
        public val random: StrictlyNegativeInt
            get() = range.random().toStrictlyNegativeInt()
    }
}

@SinceKotoolsTypes("3.0")
private class StrictlyNegativeIntImplementation(value: Int) :
    StrictlyNegativeInt,
    IntHolder by IntHolder(value, { it < 0 })

@SinceKotoolsTypes("3.0")
internal object StrictlyNegativeIntSerializer :
    IntHolderSerializer<StrictlyNegativeInt> by IntHolderSerializer(
        "StrictlyNegativeInt",
        Int::toStrictlyNegativeInt
    )
