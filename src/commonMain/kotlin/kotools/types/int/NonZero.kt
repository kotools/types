package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.tryOrNull

// ---------- Builders ----------

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
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if this value equals zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNonZeroInt(): NonZeroInt = toInt().toNonZeroInt()

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroIntOrNull(this)

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents zero.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNonZeroIntOrNull(): NonZeroInt? =
    toIntOrNull()?.toNonZeroIntOrNull()

/** Parent of classes responsible for holding integers other than zero. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroInt : IntHolder {
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

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    public companion object {
        // TODO: Use kotools.types.int.StrictlyNegativeInt.range instead
        private val negativeRange: IntRange = Int.MIN_VALUE..-1

        // TODO: Use kotools.types.int.StrictlyPositiveInt.range instead
        private val positiveRange: IntRange = 1..Int.MAX_VALUE

        // TODO: Use the NotEmptySet<IntRange> type instead
        private val ranges: Set<IntRange> = setOf(negativeRange, positiveRange)

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt = NonZeroInt(negativeRange.first)

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt = NonZeroInt(positiveRange.last)

        /** Returns a random [NonZeroInt]. */
        public val random: NonZeroInt
            get() = ranges.random()
                .random()
                .toNonZeroInt()
    }
}

@SinceKotoolsTypes("3.0")
private class NonZeroIntImplementation(value: Int) : NonZeroInt,
    IntHolder by IntHolder(value, { it != 0 })

@SinceKotoolsTypes("3.0")
internal object NonZeroIntSerializer :
    IntHolderSerializer<NonZeroInt> by IntHolderSerializer(Int::toNonZeroInt)
