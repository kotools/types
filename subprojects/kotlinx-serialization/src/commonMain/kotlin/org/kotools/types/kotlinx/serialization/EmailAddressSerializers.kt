@file:JvmName("EmailAddressSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [EmailAddress] type as
 * [String].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.EmailAddressSerializersCommonSample.stringSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun EmailAddress.Companion.stringSerializer():
        KSerializer<EmailAddress> = object : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "EmailAddressAsStringSerializer",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress = decoder
        .decodeString()
        .let(this@stringSerializer::orThrow)
}
