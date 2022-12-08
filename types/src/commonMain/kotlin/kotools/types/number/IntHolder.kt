package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

// ---------- Binary operations ----------

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotools(Types, "3.0")
public operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Adds the [other] value to this value. */
@SinceKotools(Types, "3.0")
public operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotools(Types, "3.0")
public operator fun Int.minus(other: IntHolder): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotools(Types, "3.0")
public operator fun Int.times(other: IntHolder): Int = times(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotools(Types, "3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the maximum, it returns the minimum value instead.
     */
    public operator fun inc(): IntHolder

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the minimum, it returns the maximum value instead.
     */
    public operator fun dec(): IntHolder

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): IntHolder

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
