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
internal class ZeroAsDoubleSerializer : KSerializer<Zero> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.DOUBLE
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean = other is ZeroAsDoubleSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: Zero): Unit = value
        .toDouble()
        .let(encoder::encodeDouble)

    override fun deserialize(decoder: Decoder): Zero = decoder.decodeDouble()
        .let(Zero.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
