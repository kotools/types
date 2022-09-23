package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

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

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * strictly negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toPositiveInt(): PositiveInt = toInt().toPositiveInt()

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveIntOrNull(this)

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly negative
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toPositiveIntOrNull(): PositiveInt? =
    toIntOrNull()?.toPositiveIntOrNull()

/**
 * Parent of classes responsible for holding positive integers, including zero.
 */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface PositiveInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][PositiveInt.max], it returns the
     * [minimum][PositiveInt.min] value instead.
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else PositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][PositiveInt.min], it returns the
     * [maximum][PositiveInt.max] value instead.
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else PositiveInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NegativeInt = NegativeInt(-value)

    public companion object {
        private val range: IntRange = 0..Int.MAX_VALUE

        /** The minimum value of a [PositiveInt]. */
        public val min: PositiveInt = PositiveInt(range.first)

        /** The maximum value of a [PositiveInt]. */
        public val max: PositiveInt = PositiveInt(range.last)

        /** Returns a random [PositiveInt]. */
        public val random: PositiveInt get() = range.random().toPositiveInt()
    }
}

@SinceKotoolsTypes("3.0")
private class PositiveIntImplementation(value: Int) : PositiveInt,
    IntHolder by IntHolder(value, { it >= 0 })

@SinceKotoolsTypes("3.0")
internal object PositiveIntSerializer :
    IntHolderSerializer<PositiveInt> by IntHolderSerializer(
        "PositiveInt",
        Int::toPositiveInt
    )
