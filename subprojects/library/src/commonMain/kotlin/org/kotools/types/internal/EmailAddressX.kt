package org.kotools.types.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressAsStringSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor by lazy {
        val serialName: String = qualifiedNameOf<EmailAddress>()
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    }

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress {
        val value: String = decoder.decodeString()
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        if (null != address) return address
        val error = InvalidEmailAddress(value)
        throw SerializationException("$error")
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class InvalidEmailAddress(
    private val value: Any,
    private val pattern: Any = EmailAddress.PATTERN
) {
    override fun equals(other: Any?): Boolean = other is InvalidEmailAddress
            && this.value == other.value
            && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(value, pattern)

    override fun toString(): String =
        "\"$value\" is an invalid email address. " +
                "It should match the following pattern: $pattern"
}

internal class InvalidEmailAddressPattern(private val pattern: Any) {
    override fun equals(other: Any?): Boolean =
        other is InvalidEmailAddressPattern && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(pattern)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    override fun toString(): String =
        "$pattern is an invalid email address pattern. " +
                "It should match the following one: ${EmailAddress.PATTERN}"
}
