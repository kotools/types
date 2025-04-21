package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.simpleNameOf

internal interface StringSerializer<T : Any> : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(this::class),
            kind = PrimitiveKind.STRING
        )

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: T): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): T = decoder.decodeString()
        .let(this::stringToType)

    fun stringToType(text: String): T
}
