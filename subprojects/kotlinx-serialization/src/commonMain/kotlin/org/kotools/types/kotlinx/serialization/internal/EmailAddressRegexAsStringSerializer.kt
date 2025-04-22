package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi

@ExperimentalKotoolsTypesApi
internal class EmailAddressRegexAsStringSerializer :
    KSerializer<EmailAddressRegex> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.STRING
    )

    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is EmailAddressRegexAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")


    // ----------------------- Serialization operations ------------------------

    override fun serialize(encoder: Encoder, value: EmailAddressRegex): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddressRegex = decoder
        .decodeString()
        .let(EmailAddressRegex.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
