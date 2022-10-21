package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    value.toStrictlyNegativeInt()

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    value.toStrictlyNegativeIntOrNull()

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if this value is positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toStrictlyNegativeIntOrNull()
        ?: throw StrictlyNegativeInt.ConstructionError(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    takeIf { it < 0 }
        ?.let(::StrictlyNegativeIntImplementation)

/** Representation of strictly negative integers, excluding zero. */
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    // ---------- Unary operations ----------

    override fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else StrictlyNegativeInt(value + 1)

    override fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else StrictlyNegativeInt(value - 1)

    override fun unaryMinus(): StrictlyPositiveInt = StrictlyPositiveInt(-value)

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value of a [StrictlyNegativeInt]. */
        public val min: StrictlyNegativeInt by lazy {
            StrictlyNegativeInt(range.first)
        }

        /** The maximum value of a [StrictlyNegativeInt]. */
        public val max: StrictlyNegativeInt by lazy {
            StrictlyNegativeInt(range.last)
        }

        /**
         * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the StrictlyNegativeIntOrNull function instead.",
            ReplaceWith(
                "StrictlyNegativeIntOrNull(value)",
                "kotools.types.number.StrictlyNegativeIntOrNull"
            ),
            DeprecationLevel.ERROR
        )
        public infix fun orNull(value: Int): StrictlyPositiveInt? =
            value.toStrictlyPositiveIntOrNull()

        /** Returns a random [StrictlyNegativeInt]. */
        @SinceKotoolsTypes("3.0", StabilityLevel.Beta)
        public fun random(): StrictlyNegativeInt = range.random()
            .toStrictlyNegativeInt()
    }

    /** Error thrown when creating a [StrictlyNegativeInt] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyNegativeInt doesn't accept positive values (tried with " +
                "$value)."
    )
}

internal object StrictlyNegativeIntSerializer :
    IntHolder.Serializer<StrictlyNegativeInt>(::StrictlyNegativeInt)

@JvmInline
private value class StrictlyNegativeIntImplementation(override val value: Int) :
    StrictlyNegativeInt {
    override fun toString(): String = value.toString()
}
