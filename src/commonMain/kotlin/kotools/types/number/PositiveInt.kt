package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
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
public fun String.toPositiveInt(): PositiveInt =
    toInt().toPositiveInt()

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly negative
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toPositiveIntOrNull(): PositiveInt? =
    toIntOrNull()?.toPositiveIntOrNull()

/**
 * Represents positive integers, including `0`.
 *
 * @constructor Returns the [value] as a positive int, or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class PositiveInt
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsInt {
    init {
        require(value in range) {
            val type: String = this::class.simpleName!!
            "$type accepts values included in $range (tried with $value)."
        }
    }

    public companion object {
        @SinceKotoolsTypes("3.0")
        private val range: IntRange = 0..Int.MAX_VALUE

        /** The minimum value of a [PositiveInt]. */
        public val min: PositiveInt = PositiveInt(range.first)

        /** The maximum value of a [PositiveInt]. */
        public val max: PositiveInt = PositiveInt(range.last)

        /** Returns a random [PositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public val random: PositiveInt get() = range.random().toPositiveInt()

        /**
         * Returns the [value] as a [PositiveInt], or returns `null` if the
         * [value] is strictly negative.
         */
        public infix fun orNull(value: Int): PositiveInt? = try {
            PositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
