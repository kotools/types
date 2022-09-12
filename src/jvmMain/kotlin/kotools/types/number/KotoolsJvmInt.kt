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

/** Parent of every integer's representation in this library. */
@Serializable(KotoolsJvmIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface KotoolsJvmInt : KotoolsInt {
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
