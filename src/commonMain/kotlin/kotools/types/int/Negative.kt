package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

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

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * strictly positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeInt(): NegativeInt = toInt().toNegativeInt()

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeIntOrNull(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly positive
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNegativeIntOrNull(): NegativeInt? =
    toIntOrNull()?.toNegativeIntOrNull()

/**
 * Parent of classes responsible for holding negative integers, including zero.
 */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface NegativeInt : IntHolder {
    public companion object {
        private val range: IntRange = Int.MIN_VALUE..0

        /** The minimum value of a [NegativeInt]. */
        public val min: NegativeInt = NegativeInt(range.first)

        /** The maximum value of a [NegativeInt]. */
        public val max: NegativeInt = NegativeInt(range.last)

        /** Returns a random [NegativeInt]. */
        public val random: NegativeInt get() = range.random().toNegativeInt()
    }
}

@SinceKotoolsTypes("3.0")
private class NegativeIntImplementation(value: Int) : NegativeInt,
    IntHolder by IntHolder(value, { it <= 0 })

@SinceKotoolsTypes("3.0")
internal object NegativeIntSerializer :
    IntHolderSerializer<NegativeInt> by IntHolderSerializer(
        "NegativeInt",
        Int::toNegativeInt
    )
