package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.minus(other: IntHolder): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.times(other: IntHolder): Int = times(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): IntHolder

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public operator fun compareTo(other: Int): Int = value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: IntHolder): Int = compareTo(other.value)

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: IntHolder): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: IntHolder): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: IntHolder): Int = times(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: NonZeroInt): Int = value / other.value

    /**
     * Parent of classes responsible for serializing or deserializing an
     * [IntHolder].
     */
    public sealed class Serializer<T : IntHolder>(
        private val builder: (Int) -> T
    ) : KSerializer<T> {
        private val delegate: KSerializer<Int> = Int.serializer()

        /**
         * Describes the structure of the serializable representation of [T],
         * produced by this serializer.
         */
        override val descriptor: SerialDescriptor = delegate.descriptor

        /**
         * Serializes the [value] of type [T] using the format represented by
         * the [encoder].
         */
        override fun serialize(encoder: Encoder, value: T): Unit =
            delegate.serialize(encoder, value.value)

        /**
         * Deserializes the value of type [T] using the format represented by
         * the [decoder].
         */
        override fun deserialize(decoder: Decoder): T =
            delegate.deserialize(decoder).let(builder)
    }
}

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if the [value] equals zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(NonZeroInt.ConstructionError::class)
public fun NonZeroInt(value: Int): NonZeroInt = NonZeroIntOrNull(value)
    ?: throw NonZeroInt.ConstructionError

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? = value.takeIf { it != 0 }
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

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = PositiveIntOrNull(value)
    ?: throw PositiveInt.ConstructionError(value)

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    value.takeIf { it >= 0 }
        ?.let(::PositiveIntImplementation)

/** Representation of positive integers, including zero. */
@Serializable(PositiveInt.Serializer::class)
@SinceKotoolsTypes("3.0")
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

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if the [value] is negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun StrictlyPositiveInt(value: Int): StrictlyPositiveInt =
    StrictlyPositiveIntOrNull(value)
        ?: throw StrictlyPositiveInt.ConstructionError(value)

/**
 * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
 * [value] is negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyPositiveIntOrNull(value: Int): StrictlyPositiveInt? =
    value.takeIf { it > 0 }
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

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if the [value] is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(NegativeInt.ConstructionError::class)
public fun NegativeInt(value: Int): NegativeInt = NegativeIntOrNull(value)
    ?: throw NegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    value.takeIf { it <= 0 }
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

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    StrictlyNegativeIntOrNull(value)
        ?: throw StrictlyNegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    value.takeIf { it < 0 }
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
