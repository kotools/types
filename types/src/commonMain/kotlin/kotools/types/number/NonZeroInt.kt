package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if the [value] equals zero.
 */
@SinceKotoolsTypes("1.1")
@Throws(NonZeroInt.ConstructionError::class)
public fun NonZeroInt(value: Int): NonZeroInt = nonZero int value

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? = nonZero intOrNull value

/**
 * Returns this value as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if this value equals zero.
 */
@SinceKotoolsTypes("1.1")
@Throws(NonZeroInt.ConstructionError::class)
public fun Int.toNonZeroInt(): NonZeroInt = toNonZeroIntOrNull()
    ?: throw NonZeroInt.ConstructionError

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = takeIf { it != 0 }
    ?.let(::NonZeroIntImplementation)

/** Returns a random [NonZeroInt]. */
@SinceKotoolsTypes("3.2")
public fun randomNonZeroInt(): NonZeroInt = NonZeroInt.ranges.random()
    .random()
    .toNonZeroInt()

// ---------- Binary operations ----------

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotoolsTypes("1.1")
public operator fun Int.div(other: NonZeroInt): Int = div(other.value)

/** Representation of integers other than zero. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NonZeroInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] equals `-1`, it returns `1` instead.
     * If this [value] is the maximum, it returns the minimum value instead.
     */
    override fun inc(): NonZeroInt = when (value) {
        -1 -> nonZero int 1
        max.value -> min
        else -> nonZero int value + 1
    }

    /**
     * Returns this [value] decremented by one.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    override fun dec(): NonZeroInt = when (value) {
        1 -> nonZero int -1
        min.value -> max
        else -> nonZero int value - 1
    }

    override fun unaryMinus(): NonZeroInt = nonZero int -value

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        nonZero int value * other.value

    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        private val negativeRange: IntRange by lazy(
            StrictlyNegativeInt.Companion::range
        )
        private val positiveRange: IntRange by lazy(
            StrictlyPositiveInt.Companion::range
        )
        internal val ranges: Set<IntRange> by lazy {
            setOf(negativeRange, positiveRange)
        }

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt by lazy { nonZero int negativeRange.first }

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt by lazy { nonZero int positiveRange.last }

        /**
         * Returns the [value] as a [NonZeroInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NonZeroIntOrNull function instead.",
            ReplaceWith(
                "NonZeroIntOrNull(value)",
                "kotools.types.number.NonZeroIntOrNull"
            ),
            DeprecationLevel.ERROR
        )
        public infix fun orNull(value: Int): NonZeroInt? =
            nonZero intOrNull value

        /** Returns a random [NonZeroInt]. */
        @Deprecated(
            "Use the randomNonZeroInt function instead. " +
                    "Will be an error in v3.3.",
            ReplaceWith(
                "randomNonZeroInt()",
                "kotools.types.number.randomNonZeroInt"
            )
        )
        @SinceKotoolsTypes("3.0")
        public fun random(): NonZeroInt = ranges.random()
            .random()
            .toNonZeroInt()
    }

    /** Error thrown when creating a [NonZeroInt] fails. */
    @SinceKotoolsTypes("3.0")
    public object ConstructionError :
        IllegalArgumentException("NonZeroInt doesn't accept 0.")
}

internal object NonZeroIntSerializer :
    IntHolder.Serializer<NonZeroInt>(nonZero::int)

@JvmInline
private value class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt {
    override fun toString(): String = value.toString()
}
