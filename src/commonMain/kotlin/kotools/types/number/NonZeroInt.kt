package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if this value equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNonZeroInt(): NonZeroInt = toInt().toNonZeroInt()

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * `0`.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull this

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents `0`.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNonZeroIntOrNull(): NonZeroInt? =
    toIntOrNull()?.toNonZeroIntOrNull()

/**
 * Represents integers that don't equal `0`.
 *
 * @constructor Returns the [value] as a non-zero int, or throws an
 * [IllegalArgumentException] if the [value] equals `0`.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NonZeroInt
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsInt {
    init {
        val valueIsInRanges: Boolean = ranges.any { value in it }
        require(valueIsInRanges) {
            val type: String = this::class.simpleName!!
            "$type doesn't accept 0."
        }
    }

    public companion object {
        // TODO: Use StrictlyNegativeInt.range instead.
        @SinceKotoolsTypes("3.0")
        internal val negativeRange: IntRange = Int.MIN_VALUE..-1

        // TODO: Use StrictlyPositiveInt.range instead.
        @SinceKotoolsTypes("3.0")
        internal val positiveRange: IntRange = 1..Int.MAX_VALUE

        @SinceKotoolsTypes("3.0")
        internal val ranges: Set<IntRange> = setOf(negativeRange, positiveRange)

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt = NonZeroInt(negativeRange.first)

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt = NonZeroInt(positiveRange.last)

        /** Returns a random [NonZeroInt]. */
        @SinceKotoolsTypes("3.0")
        public val random: NonZeroInt
            get() = ranges.random()
                .random()
                .toNonZeroInt()

        /**
         * Returns the [value] as a [NonZeroInt], or returns `null` if the
         * [value] equals `0`.
         */
        public infix fun orNull(value: Int): NonZeroInt? = try {
            NonZeroInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
