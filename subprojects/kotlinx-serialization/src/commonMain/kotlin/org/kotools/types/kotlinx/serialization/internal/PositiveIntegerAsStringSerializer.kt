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
import org.kotools.types.PositiveInteger

@ExperimentalKotoolsTypesApi
internal class PositiveIntegerAsStringSerializer :
    KSerializer<PositiveInteger> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.STRING
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is PositiveIntegerAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: PositiveInteger): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): PositiveInteger = decoder
        .decodeString()
        .let(PositiveInteger.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
