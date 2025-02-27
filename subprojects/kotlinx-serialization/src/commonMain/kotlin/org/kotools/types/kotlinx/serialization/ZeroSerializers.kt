@file:JvmName("ZeroSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [Zero] type as [Byte].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersCommonSample.byteSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.byteSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsByteSerializer",
            PrimitiveKind.BYTE
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toByte()
            .let(encoder::encodeByte)

        override fun deserialize(decoder: Decoder): Zero = decoder.decodeByte()
            .let(this@byteSerializer::orThrow)
    }
