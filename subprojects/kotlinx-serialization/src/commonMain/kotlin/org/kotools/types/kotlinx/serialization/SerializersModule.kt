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
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns a collection of default serializers for types located in
 * `org.kotools.types` package.
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
 * Here's an example of calling it, using the JavaScript Object Notation (JSON)
 * format:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressAsString]
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
 * Here's an example of calling it, using the JavaScript Object Notation (JSON)
 * format:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressRegexAsString]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
@Suppress(Warning.FUNCTION_NAME)
public fun KotoolsTypesSerializersModule(): SerializersModule =
    SerializersModule {
        this.contextual(EmailAddressAsStringSerializer())
        this.contextual(EmailAddressRegexAsStringSerializer())
    }

@OptIn(ExperimentalKotoolsTypesApi::class)
private class EmailAddressAsStringSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor
        get() {
            val serialName: String? = EmailAddress::class.simpleName
            checkNotNull(serialName) { "Serial name can't be null." }
            return PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
        }

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress {
        val text: String = decoder.decodeString()
        return requireNotNull(EmailAddress of text) {
            "Invalid email address (was: $text)."
        }
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class EmailAddressRegexAsStringSerializer :
    KSerializer<EmailAddressRegex> {
    override val descriptor: SerialDescriptor
        get() {
            val serialName: String? = EmailAddressRegex::class.simpleName
            checkNotNull(serialName) { "Serial name can't be null." }
            return PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
        }

    override fun serialize(encoder: Encoder, value: EmailAddressRegex): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddressRegex {
        val text: String = decoder.decodeString()
        return requireNotNull(EmailAddressRegex of text) {
            "Invalid email address regex (was: $text)."
        }
    }
}
