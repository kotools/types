package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal interface StringSerializer<T : Any> : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() {
            val serialName: String = this.toString()
            return PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
        }

    override fun serialize(encoder: Encoder, value: T) {
        val stringValue: String = value.toString()
        encoder.encodeString(stringValue)
    }

    override fun deserialize(decoder: Decoder): T {
        val text: String = decoder.decodeString()
        return this.deserialize(text)
    }

    fun deserialize(text: String): T
}
