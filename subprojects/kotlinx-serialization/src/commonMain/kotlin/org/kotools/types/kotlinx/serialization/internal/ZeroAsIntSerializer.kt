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
internal class ZeroAsIntSerializer : KSerializer<Zero> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.INT
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean = other is ZeroAsIntSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: Zero): Unit = value.toInt()
        .let(encoder::encodeInt)

    override fun deserialize(decoder: Decoder): Zero = decoder.decodeInt()
        .let(Zero.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
