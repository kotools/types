package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a strictly positive int, or throws an
 * [IllegalArgumentException] if this value is [negative][NegativeInt].
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a strictly positive int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * [negative number][NegativeInt].
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is [negative][NegativeInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * [negative number][NegativeInt].
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    toIntOrNull()?.toStrictlyPositiveIntOrNull()

/**
 * Represents strictly positive integers (`x > 0`).
 *
 * @constructor Returns the [value] as a strictly positive int, or throws an
 * [IllegalArgumentException] if the [value] is [negative][NegativeInt].
 */
@JvmInline
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
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
        internal val range: IntRange = 1..PositiveInt.max.value

        /** The minimum value of a strictly positive int. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(range.first)

        /** The maximum value of a strictly positive int. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(range.last)

        /** Returns a random strictly positive int. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyPositiveInt
            get() = range.random().toStrictlyPositiveInt()

        /**
         * Returns the [value] as a strictly positive int, or returns `null` if
         * the [value] is [negative][NegativeInt].
         */
        public infix fun orNull(value: Int): StrictlyPositiveInt? = try {
            StrictlyPositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
