package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

// TODO: Add the link [negative][NegativeInt] in the documentation.
/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

// TODO: Add the link [negative number][NegativeInt] in the documentation.
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

// TODO: Add the link [negative number][NegativeInt] in the documentation.
/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

// TODO: Add the link [negative number][NegativeInt] in the documentation.
/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    toIntOrNull()?.toStrictlyPositiveIntOrNull()

// TODO: Add the link [negative][NegativeInt] in the documentation.
/**
 * Represents strictly positive integers, excluding `0`.
 *
 * @constructor Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is negative.
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

        /** The minimum value of a [StrictlyPositiveInt]. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(range.first)

        /** The maximum value of a [StrictlyPositiveInt]. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(range.last)

        /** Returns a random [StrictlyPositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyPositiveInt
            get() = range.random().toStrictlyPositiveInt()

        // TODO: Add the link [negative][NegativeInt] in the documentation.
        /**
         * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if
         * the [value] is negative.
         */
        public infix fun orNull(value: Int): StrictlyPositiveInt? = try {
            StrictlyPositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
