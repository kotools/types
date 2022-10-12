package kotools.types

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if the [value] is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(NegativeInt.ConstructionError::class)
public fun NegativeInt(value: Int): NegativeInt = value.toNegativeInt()

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    value.toNegativeIntOrNull()

/**
 * Returns this value as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if this value is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(NegativeInt.ConstructionError::class)
public fun Int.toNegativeInt(): NegativeInt = toNegativeIntOrNull()
    ?: throw NegativeInt.ConstructionError(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNegativeIntOrNull(): NegativeInt? = takeIf { it <= 0 }
    ?.let(::NegativeIntImplementation)

/** Representation of negative integers, including zero. */
@Serializable(NegativeInt.Serializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface NegativeInt : IntHolder {
    override fun unaryMinus(): PositiveInt = PositiveInt(-value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(value / other.value)

    /** Error thrown when creating a [NegativeInt] fails. */
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "NegativeInt doesn't accept strictly positive values (tried with " +
                "$value)."
    )

    /** Object responsible for serializing or deserializing a [NegativeInt]. */
    public object Serializer : IntHolder.Serializer<NegativeInt>(::NegativeInt)
}

@JvmInline
private value class NegativeIntImplementation(override val value: Int) :
    NegativeInt
