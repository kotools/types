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
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.byteSerializer]
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

/**
 * Returns an object responsible for serializing the [Zero] type as [Short].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.shortSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.shortSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsShortSerializer",
            PrimitiveKind.SHORT
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toShort()
            .let(encoder::encodeShort)

        override fun deserialize(decoder: Decoder): Zero = decoder.decodeShort()
            .let(this@shortSerializer::orThrow)
    }

/**
 * Returns an object responsible for serializing the [Zero] type as [Int].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.intSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.intSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsIntSerializer",
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toInt()
            .let(encoder::encodeInt)

        override fun deserialize(decoder: Decoder): Zero = decoder.decodeShort()
            .let(this@intSerializer::orThrow)
    }

/**
 * Returns an object responsible for serializing the [Zero] type as [Long].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.longSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.longSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsLongSerializer",
            PrimitiveKind.LONG
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toLong()
            .let(encoder::encodeLong)

        override fun deserialize(decoder: Decoder): Zero = decoder.decodeLong()
            .let(this@longSerializer::orThrow)
    }

/**
 * Returns an object responsible for serializing the [Zero] type as [Float].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.floatSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.floatSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsFloatSerializer",
            PrimitiveKind.FLOAT
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toFloat()
            .let(encoder::encodeFloat)

        override fun deserialize(decoder: Decoder): Zero = decoder.decodeFloat()
            .let(this@floatSerializer::orThrow)
    }

/**
 * Returns an object responsible for serializing the [Zero] type as [Double].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.doubleSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.doubleSerializer(): KSerializer<Zero> =
    object : KSerializer<Zero> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsDoubleSerializer",
            PrimitiveKind.DOUBLE
        )

        override fun serialize(encoder: Encoder, value: Zero): Unit = value
            .toDouble()
            .let(encoder::encodeDouble)

        override fun deserialize(decoder: Decoder): Zero = decoder
            .decodeDouble()
            .let(this@doubleSerializer::orThrow)
    }
