package org.kotools.types.kotlinx.serialization

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
import org.kotools.types.internal.DeprecatedAsErrorSince
import org.kotools.types.internal.DeprecatedAsWarningSince
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.reflect.KClass

/**
 * Class responsible for serializing the [Zero] type as [Byte].
 *
 * @constructor Creates an instance of [ZeroAsByteSerializer].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_3)
@Deprecated(
    message = "Use the serializer returned by the " +
            "'Zero.Companion.byteSerializer' function instead."
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V5_0_1)
public class ZeroAsByteSerializer
@Deprecated(
    message = "Use the 'Zero.Companion.byteSerializer' function instead.",
    ReplaceWith(
        "Zero.byteSerializer()",
        "org.kotools.types.Zero",
        "org.kotools.types.kotlinx.serialization.byteSerializer"
    ),
    DeprecationLevel.ERROR
)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_1)
public constructor() : KSerializer<Zero> {
    /**
     * Describes the structure of the serializable representation of [Zero],
     * produced by this serializer.
     *
     * See the [KSerializer.descriptor] property for more details.
     */
    @Suppress(Warning.FINAL)
    final override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.BYTE
    )

    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [ZeroAsByteSerializer], or returns `false` otherwise.
     */
    @Suppress(Warning.FINAL, "DEPRECATION")
    final override fun equals(other: Any?): Boolean =
        other is ZeroAsByteSerializer

    /** Returns a hash code value for this serializer. */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    /**
     * Serializes the specified [value] using the format represented by the
     * specified [encoder].
     *
     * See the [KSerializer.serialize] function for more details.
     */
    @Suppress(Warning.FINAL)
    final override fun serialize(encoder: Encoder, value: Zero) {
        val valueAsByte: Byte = value.toByte()
        encoder.encodeByte(valueAsByte)
    }

    /**
     * Deserializes the value of type [Zero] using the format represented by the
     * specified [decoder].
     *
     * See the [KSerializer.deserialize] function for more details.
     */
    @Suppress(Warning.FINAL)
    final override fun deserialize(decoder: Decoder): Zero {
        val number: Byte = decoder.decodeByte()
        return Zero.orThrow(number)
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this serializer, corresponding to
     * its [simple name][KClass.simpleName].
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
