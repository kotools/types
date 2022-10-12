package kotools.types

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@SinceKotoolsTypes("3.0")
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
@SinceKotoolsTypes("3.0")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toStrictlyNegativeIntOrNull()
        ?: throw StrictlyNegativeInt.ConstructionError(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    takeIf { it < 0 }
        ?.let(::StrictlyNegativeIntImplementation)

/** Representation of strictly negative integers, excluding zero. */
@Serializable(StrictlyNegativeInt.Serializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    override fun unaryMinus(): StrictlyPositiveInt = StrictlyPositiveInt(-value)

    /** Error thrown when creating a [StrictlyNegativeInt] fails. */
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyNegativeInt doesn't accept positive values (tried with " +
                "$value)."
    )

    /**
     * Object responsible for serializing or deserializing a
     * [StrictlyNegativeInt].
     */
    public object Serializer :
        IntHolder.Serializer<StrictlyNegativeInt>(::StrictlyNegativeInt)
}

@JvmInline
private value class StrictlyNegativeIntImplementation(override val value: Int) :
    StrictlyNegativeInt
