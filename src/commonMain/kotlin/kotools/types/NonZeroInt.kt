package kotools.types

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if the [value] equals zero.
 */
@SinceKotoolsTypes("3.0")
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
@SinceKotoolsTypes("3.0")
@Throws(NonZeroInt.ConstructionError::class)
public fun Int.toNonZeroInt(): NonZeroInt = toNonZeroIntOrNull()
    ?: throw NonZeroInt.ConstructionError

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = takeIf { it != 0 }
    ?.let(::NonZeroIntImplementation)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.div(other: NonZeroInt): Int = div(other.value)

/** Representation of integers other than zero. */
@Serializable(NonZeroInt.Serializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroInt : IntHolder {
    override fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(value * other.value)

    /** Error thrown when creating a [NonZeroInt] fails. */
    public object ConstructionError :
        IllegalArgumentException("NonZeroInt doesn't accept 0.")

    /** Object responsible for serializing or deserializing a [NonZeroInt]. */
    public object Serializer : IntHolder.Serializer<NonZeroInt>(::NonZeroInt)
}

@JvmInline
private value class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt
