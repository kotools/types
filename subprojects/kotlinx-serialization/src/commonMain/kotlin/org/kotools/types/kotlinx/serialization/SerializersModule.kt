@file:JvmName("SerializersModule")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns a collection of default serializers for types located in
 * `org.kotools.types` package.
 *
 * Note: All samples documented below use the JavaScript Object Notation (JSON)
 * format.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>EmailAddress</b>
 * </summary>
 *
 * This function provides an object for serializing and deserializing an
 * [EmailAddress] as [String].
 *
 * SAMPLE: org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressAsString
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>EmailAddressRegex</b>
 * </summary>
 *
 * This function provides an object for serializing and deserializing an
 * [EmailAddressRegex] as [String].
 *
 * SAMPLE: org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressRegexAsString
 * </details>
 *
 * @since 5.0.1
 */
@ExperimentalKotoolsTypesApi
@JvmSynthetic
@Suppress("FunctionName")
public fun KotoolsTypesSerializersModule(): SerializersModule =
    SerializersModule {
        this.contextual(EmailAddressAsStringSerializer())
        this.contextual(EmailAddressRegexAsStringSerializer())
    }

@OptIn(ExperimentalKotoolsTypesApi::class)
private class EmailAddressAsStringSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = SerialName.EmailAddress.toString(),
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress {
        val text: String = decoder.decodeString()
        return checkNotNull(EmailAddress of text) {
            errorMessage("Invalid email address", text)
        }
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class EmailAddressRegexAsStringSerializer :
    KSerializer<EmailAddressRegex> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = SerialName.EmailAddressRegex.toString(),
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: EmailAddressRegex): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddressRegex {
        val text: String = decoder.decodeString()
        return checkNotNull(EmailAddressRegex of text) {
            errorMessage("Invalid email address regex", text)
        }
    }
}

private enum class SerialName(private val value: String) {
    EmailAddress("org.kotools.types.EmailAddress"),
    EmailAddressRegex("org.kotools.types.EmailAddressRegex");

    override fun toString(): String = this.value
}
