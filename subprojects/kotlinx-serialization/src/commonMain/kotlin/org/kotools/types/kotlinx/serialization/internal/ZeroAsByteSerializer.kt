package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroAsByteSerializer : KSerializer<Zero> {
    override val descriptor: SerialDescriptor
        get() {
            val serialName: String = serialNameOf<Zero>()
            return PrimitiveSerialDescriptor(serialName, PrimitiveKind.BYTE)
        }

    override fun serialize(encoder: Encoder, value: Zero) {
        val valueAsByte: Byte = value.toByte()
        encoder.encodeByte(valueAsByte)
    }

    override fun deserialize(decoder: Decoder): Zero {
        val decodedValue: Byte = decoder.decodeByte()
        val zero: Zero? = Zero.fromByteOrNull(decodedValue)
        if (zero != null) return zero
        val reason = "It should be equal to zero."
        val error =
            DeserializationError(deserializer = this, decodedValue, reason)
        throw SerializationException("$error")
    }
}
