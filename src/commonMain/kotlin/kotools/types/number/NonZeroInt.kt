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
public fun NonZeroInt(value: Int): NonZeroInt = value.toNonZeroInt()

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? =
    value.toNonZeroIntOrNull()

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

    override fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        private val negativeRange: IntRange = StrictlyNegativeInt.range
        private val positiveRange: IntRange = StrictlyPositiveInt.range
        internal val ranges: Set<IntRange> = setOf(negativeRange, positiveRange)

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt = NonZeroInt(negativeRange.first)

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt = NonZeroInt(positiveRange.last)

        /**
         * Returns the [value] as a [NonZeroInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NonZeroIntOrNull function instead.",
            ReplaceWith(
                "NonZeroIntOrNull(value)",
                "kotools.types.number.NonZeroIntOrNull"
            )
        )
        public infix fun orNull(value: Int): NonZeroInt? =
            value.toNonZeroIntOrNull()
    }

    /** Error thrown when creating a [NonZeroInt] fails. */
    @SinceKotoolsTypes("3.0")
    public object ConstructionError :
        IllegalArgumentException("NonZeroInt doesn't accept 0.")
}

internal object NonZeroIntSerializer :
    IntHolder.Serializer<NonZeroInt>(::NonZeroInt)

@JvmInline
private value class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt {
    override fun toString(): String = value.toString()
}
