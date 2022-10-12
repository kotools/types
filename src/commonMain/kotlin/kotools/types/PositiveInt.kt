package kotools.types

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = value.toPositiveInt()

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    value.toPositiveIntOrNull()

/**
 * Returns this value as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if this value is strictly negative.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
@Throws(PositiveInt.ConstructionError::class)
public fun Int.toPositiveInt(): PositiveInt = toPositiveIntOrNull()
    ?: throw PositiveInt.ConstructionError(this)

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
public fun Int.toPositiveIntOrNull(): PositiveInt? = takeIf { it >= 0 }
    ?.let(::PositiveIntImplementation)

/** Representation of positive integers, including zero. */
@Serializable(PositiveInt.Serializer::class)
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
public sealed interface PositiveInt : IntHolder {
    override fun unaryMinus(): NegativeInt = NegativeInt(-value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(value / other.value)

    /** Error thrown when creating a [PositiveInt] fails. */
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "PositiveInt doesn't accept strictly negative values (tried with " +
                "$value)."
    )

    /** Object responsible for serializing or deserializing a [PositiveInt]. */
    public object Serializer : IntHolder.Serializer<PositiveInt>(::PositiveInt)
}

@JvmInline
private value class PositiveIntImplementation(override val value: Int) :
    PositiveInt