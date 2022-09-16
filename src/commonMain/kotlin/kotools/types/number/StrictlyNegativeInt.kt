package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a strictly negative int, or throws an
 * [IllegalArgumentException] if this value is [positive][PositiveInt].
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a strictly negative int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * [positive number][PositiveInt].
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toInt().toStrictlyNegativeInt()

/**
 * Returns this value as a strictly negative int, or returns `null` if this
 * value is [positive][PositiveInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    StrictlyNegativeInt orNull this

/**
 * Returns this value as a strictly negative int, or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * [positive number][PositiveInt].
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    toIntOrNull()?.toStrictlyNegativeIntOrNull()

/**
 * Represents strictly negative integers (`x < 0`).
 *
 * @constructor Returns the [value] as a strictly negative int, or throws an
 * [IllegalArgumentException] if the [value] is [positive][PositiveInt].
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
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
        internal val range: IntRange = NegativeInt.min.value..-1

        /** The minimum value of a strictly negative int. */
        public val min: StrictlyNegativeInt = StrictlyNegativeInt(range.first)

        /** The maximum value of a strictly negative int. */
        public val max: StrictlyNegativeInt = StrictlyNegativeInt(range.last)

        /** Returns a random strictly negative int. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyNegativeInt
            get() = range.random().toStrictlyNegativeInt()

        /**
         * Returns the [value] as a strictly negative int, or returns `null` if
         * the [value] is [positive][PositiveInt].
         */
        public infix fun orNull(value: Int): StrictlyNegativeInt? = try {
            StrictlyNegativeInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}