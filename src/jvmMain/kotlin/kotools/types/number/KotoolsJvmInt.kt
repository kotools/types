package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankString

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.plus(other: KotoolsJvmInt): Int =
    plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.minus(other: KotoolsJvmInt): Int =
    minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.times(other: KotoolsJvmInt): Int =
    times(other.value)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public infix operator fun Int.div(other: KotoolsJvmInt): Int = div(other.value)

// ---------- Comparisons ----------

/**
 * Compares this value with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.compareTo(other: KotoolsJvmInt): Int =
    compareTo(other.value)

/** Parent of every integer's representation in this library. */
@Serializable(KotoolsJvmIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface KotoolsJvmInt : Comparable<Int> {
    public val value: Int

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: KotoolsJvmInt): Int =
        plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: KotoolsJvmInt): Int =
        minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: KotoolsJvmInt): Int =
        times(other.value)

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: KotoolsJvmInt): Int = div(other.value)

    // ---------- Comparisons ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: Int): Int = value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: KotoolsJvmInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this [value] as a not blank string.
     */
    public fun toNotBlankString(): NotBlankString {
        val string: String = value.toString()
        return NotBlankString(string)
    }
}

@SinceKotoolsTypes("3.0")
internal object KotoolsJvmIntSerializer : KSerializer<KotoolsJvmInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        KotoolsJvmInt::class.qualifiedName!!,
        PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: KotoolsJvmInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): KotoolsJvmInt {
        val value: Int = decoder.decodeInt()
        return if (value != 0) value.toNonZeroInt() else PositiveInt(0)
    }
}
