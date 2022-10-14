package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if the [value] is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun StrictlyPositiveInt(value: Int): StrictlyPositiveInt =
    value.toStrictlyPositiveInt()

/**
 * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
 * [value] is negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyPositiveIntOrNull(value: Int): StrictlyPositiveInt? =
    value.toStrictlyPositiveIntOrNull()

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toStrictlyPositiveIntOrNull()
        ?: throw StrictlyPositiveInt.ConstructionError(this)

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    takeIf { it > 0 }
        ?.let(::StrictlyPositiveIntImplementation)

/** Representation of strictly positive integers, excluding zero. */
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface StrictlyPositiveInt : NonZeroInt,
    PositiveInt {
    // ---------- Unary operations ----------

    override fun unaryMinus(): StrictlyNegativeInt = StrictlyNegativeInt(-value)

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange = 1..Int.MAX_VALUE

        /**
         * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if
         * the [value] equals 0.
         */
        @Deprecated(
            "Use the StrictlyPositiveIntOrNull function instead.",
            ReplaceWith(
                "StrictlyPositiveIntOrNull(value)",
                "kotools.types.number.StrictlyPositiveIntOrNull"
            )
        )
        public infix fun orNull(value: Int): StrictlyPositiveInt? =
            value.toStrictlyPositiveIntOrNull()
    }

    /** Error thrown when creating a [StrictlyPositiveInt] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyPositiveInt doesn't accept negative values (tried with " +
                "$value)."
    )
}

internal object StrictlyPositiveIntSerializer :
    IntHolder.Serializer<StrictlyPositiveInt>(::StrictlyPositiveInt)

@JvmInline
private value class StrictlyPositiveIntImplementation(override val value: Int) :
    StrictlyPositiveInt {
    override fun toString(): String = value.toString()
}
