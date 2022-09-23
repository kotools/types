package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.tryOrNull

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
    public companion object {
        private val range: IntRange = NegativeInt.min.value..-1

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
        Int::toStrictlyNegativeInt
    )
