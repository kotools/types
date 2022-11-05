package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    strictlyNegative int value

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    strictlyNegative intOrNull value

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

/** Returns a random [StrictlyNegativeInt]. */
@SinceKotoolsTypes("3.2")
public fun randomStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt.range.random()
        .toStrictlyNegativeInt()

/** Representation of strictly negative integers, excluding zero. */
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    // ---------- Unary operations ----------

    override fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else strictlyNegative int value + 1

    override fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else strictlyNegative int value - 1

    override fun unaryMinus(): StrictlyPositiveInt = strictlyPositive int -value

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value of a [StrictlyNegativeInt]. */
        public val min: StrictlyNegativeInt by lazy {
            strictlyNegative int range.first
        }

        /** The maximum value of a [StrictlyNegativeInt]. */
        public val max: StrictlyNegativeInt by lazy {
            strictlyNegative int range.last
        }

        /** Returns a random [StrictlyNegativeInt]. */
        @Deprecated(
            """
                Use the randomStrictlyNegativeInt function instead.
                Will be an error in v3.3.
            """,
            ReplaceWith(
                "randomStrictlyNegativeInt()",
                "${Package.number}.randomStrictlyNegativeInt"
            )
        )
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyNegativeInt =
            strictlyNegative int range.random()
    }

    /** Error thrown when creating a [StrictlyNegativeInt] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyNegativeInt doesn't accept positive values (tried with " +
                "$value)."
    )
}

internal object StrictlyNegativeIntSerializer :
    IntHolder.Serializer<StrictlyNegativeInt>(strictlyNegative::int)

@JvmInline
private value class StrictlyNegativeIntImplementation(override val value: Int) :
    StrictlyNegativeInt {
    override fun toString(): String = value.toString()
}
