package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.tryOrNull

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
