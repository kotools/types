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
import kotools.types.string.toNotBlankString

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.plus(other: KotoolsInt): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.minus(other: KotoolsInt): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.times(other: KotoolsInt): Int = times(other.value)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public expect infix operator fun Int.div(other: KotoolsInt): Int

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public expect infix operator fun KotoolsInt.div(other: Int): Int

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public expect infix operator fun KotoolsInt.div(other: KotoolsInt): Int

// ---------- Comparisons ----------

/**
 * Compares this value with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.compareTo(other: KotoolsInt): Int =
    compareTo(other.value)

/** Parent of every integer's representation in this library. */
@Serializable(KotoolsIntSerializer::class)
@SinceKotoolsTypes("3.0")
public interface KotoolsInt : Comparable<Int> {
    public val value: Int

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: KotoolsInt): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: KotoolsInt): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: KotoolsInt): Int = times(other.value)

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
    public infix operator fun compareTo(other: KotoolsInt): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        value.toString().toNotBlankString()
}

@SinceKotoolsTypes("3.0")
internal object KotoolsIntSerializer : KSerializer<KotoolsInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "kotools.types.number.KotoolsInt",
        PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: KotoolsInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): KotoolsInt =
        TODO("Needs PositiveInt to be available on all platforms.")
}
