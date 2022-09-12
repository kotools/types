package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.string.NotBlankStringJvm

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun Int.div(other: KotoolsInt): Int =
    div(other.value)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun KotoolsInt.div(other: Int): Int = value / other

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException] if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun KotoolsInt.div(other: KotoolsInt): Int =
    div(other.value)

/** Parent of every integer's representation in this library. */
@Serializable(KotoolsIntJvmSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface KotoolsIntJvm : KotoolsInt {
    /**
     * Returns the string representation of this [value] as a not blank string.
     */
    public fun toNotBlankString(): NotBlankStringJvm {
        val string: String = value.toString()
        return NotBlankStringJvm(string)
    }
}

@SinceKotoolsTypes("3.0")
internal object KotoolsIntJvmSerializer : KSerializer<KotoolsIntJvm> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        KotoolsIntJvm::class.qualifiedName!!,
        PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: KotoolsIntJvm): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): KotoolsIntJvm {
        val value: Int = decoder.decodeInt()
        return if (value != 0) value.toNonZeroInt() else PositiveInt(0)
    }
}
