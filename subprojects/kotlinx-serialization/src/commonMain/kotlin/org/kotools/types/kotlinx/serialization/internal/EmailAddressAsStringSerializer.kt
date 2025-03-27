package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi

@ExperimentalKotoolsTypesApi
internal class EmailAddressAsStringSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.STRING
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is EmailAddressAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress = decoder
        .decodeString()
        .let(EmailAddress.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
