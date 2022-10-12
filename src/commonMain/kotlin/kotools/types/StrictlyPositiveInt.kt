package kotools.types

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if the [value] is negative.
 */
@SinceKotoolsTypes("3.0")
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
@SinceKotoolsTypes("3.0")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toStrictlyPositiveIntOrNull()
        ?: throw StrictlyPositiveInt.ConstructionError(this)

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    takeIf { it > 0 }
        ?.let(::StrictlyPositiveIntImplementation)

/** Representation of strictly positive integers, excluding zero. */
@Serializable(StrictlyPositiveInt.Serializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyPositiveInt : NonZeroInt,
    PositiveInt {
    override fun unaryMinus(): StrictlyNegativeInt = StrictlyNegativeInt(-value)

    /** Error thrown when creating a [StrictlyPositiveInt] fails. */
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyPositiveInt doesn't accept negative values (tried with " +
                "$value)."
    )

    /**
     * Object responsible for serializing or deserializing a
     * [StrictlyPositiveInt].
     */
    public object Serializer :
        IntHolder.Serializer<StrictlyPositiveInt>(::StrictlyPositiveInt)
}

@JvmInline
private value class StrictlyPositiveIntImplementation(override val value: Int) :
    StrictlyPositiveInt
