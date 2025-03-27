package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero

@ExperimentalKotoolsTypesApi
internal class ZeroAsByteSerializer : KSerializer<Zero> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.BYTE
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean = other is ZeroAsByteSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: Zero): Unit = value.toByte()
        .let(encoder::encodeByte)

    override fun deserialize(decoder: Decoder): Zero = decoder.decodeByte()
        .let(Zero.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
