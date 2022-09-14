package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a negative int, or throws an
 * [IllegalArgumentException] if this value is
 * [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a negative int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * [strictly positive number][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeInt(): NegativeInt = toInt().toNegativeInt()

/**
 * Returns this value as a negative int, or returns `null` if this value is
 * [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull this

/**
 * Returns this value as a negative int, or returns `null` if this value is not
 * a valid representation of a number or if it represents a
 * [strictly positive number][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("3.0")
public fun String.toNegativeIntOrNull(): NegativeInt? =
    toIntOrNull()?.toNegativeIntOrNull()

/**
 * Represents negative integers (`x <= 0`).
 *
 * @constructor Returns the [value] as a negative int, or throws an
 * [IllegalArgumentException] if the [value] is
 * [strictly positive][StrictlyPositiveInt].
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class NegativeInt
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
        private val range: IntRange = Int.MIN_VALUE..0

        /** The minimum value of a negative int. */
        public val min: NegativeInt = NegativeInt(range.first)

        /** The maximum value of a negative int. */
        public val max: NegativeInt = NegativeInt(range.last)

        /** Returns a random negative int. */
        @SinceKotoolsTypes("3.0")
        public val random: NegativeInt get() = range.random().toNegativeInt()

        /**
         * Returns the [value] as a negative int, or returns `null` if the
         * [value] is [strictly positive][StrictlyPositiveInt].
         */
        public infix fun orNull(value: Int): NegativeInt? = try {
            NegativeInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
