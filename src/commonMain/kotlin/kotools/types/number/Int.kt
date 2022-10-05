package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.Holder
import kotools.types.core.HolderCompanion
import kotools.types.core.SinceKotoolsTypes
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

// ---------- IntHolder ----------

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
public sealed interface IntHolder : Holder<Int> {
    // ---------- Binary operations ----------

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
    public operator fun compareTo(other: IntHolder): Int =
        compareTo(other.value)

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
    public operator fun div(other: NonZeroIntHolder): Int = value / other.value

    // ---------- Conversions ----------

    /**
     * Returns the string representation of the [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        toString().toNotBlankString()
}

internal sealed class IntHolderSerializer<T : IntHolder>(
    private val builder: (Int) -> T
) : KSerializer<T> {
    private val delegate: KSerializer<Int> = Int.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: T): Unit =
        delegate.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder): T {
        val value: Int = delegate.deserialize(decoder)
        return builder(value)
    }
}

// ---------- IntHolderCompanion ----------

/** Parent of companion objects in subtypes of [IntHolder]. */
@SinceKotoolsTypes("3.0")
public sealed class IntHolderCompanion<out T : IntHolder>(builder: (Int) -> T) :
    HolderCompanion<Int, T>(builder) {
    /** The minimum value of the type [T]. */
    public abstract val min: T

    /** The maximum value of the type [T]. */
    public abstract val max: T

    /** Returns an instance of type [T] holding a random value. */
    public abstract val random: T
}

// ---------- NonZeroIntHolder ----------

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.div(other: NonZeroIntHolder): Int = div(other.value)

/** Parent of classes responsible for holding non-zero integers. */
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroIntHolder : IntHolder {
    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroIntHolder): NonZeroInt =
        times(other.value).toNonZeroInt()
}

// ---------- PositiveIntHolder ----------

/** Parent of classes responsible for holding positive integers. */
@SinceKotoolsTypes("3.0")
public sealed interface PositiveIntHolder : IntHolder {
    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        super.div(other).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        super.div(other).toNegativeInt()
}

// ---------- NegativeIntHolder ----------

/** Parent of classes responsible for holding negative integers. */
@SinceKotoolsTypes("3.0")
public sealed interface NegativeIntHolder : IntHolder {
    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        super.div(other).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        super.div(other).toPositiveInt()
}
