package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

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

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a [StrictlyPositiveInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveIntOrNull(this)

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    toIntOrNull()?.toStrictlyPositiveIntOrNull()

/**
 * Parent of classes responsible for holding strictly positive integers,
 * excluding zero.
 */
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyPositiveInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][StrictlyPositiveInt.max], it returns
     * the [minimum][StrictlyPositiveInt.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else StrictlyPositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][StrictlyPositiveInt.min], it returns
     * the [maximum][StrictlyPositiveInt.max] value instead.
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else StrictlyPositiveInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyNegativeInt =
        StrictlyNegativeInt(-value)

    public companion object {
        internal val range: IntRange = 1..PositiveInt.max.value

        /** The minimum value of a [StrictlyPositiveInt]. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(range.first)

        /** The maximum value of a [StrictlyPositiveInt]. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(range.last)

        /** Returns a random [StrictlyPositiveInt]. */
        public val random: StrictlyPositiveInt
            get() = range.random().toStrictlyPositiveInt()
    }
}

@SinceKotoolsTypes("3.0")
private class StrictlyPositiveIntImplementation(value: Int) :
    StrictlyPositiveInt,
    IntHolder by IntHolder(value, { it > 0 })

@SinceKotoolsTypes("3.0")
internal object StrictlyPositiveIntSerializer :
    IntHolderSerializer<StrictlyPositiveInt> by IntHolderSerializer(
        "StrictlyPositiveInt",
        Int::toStrictlyPositiveInt
    )
