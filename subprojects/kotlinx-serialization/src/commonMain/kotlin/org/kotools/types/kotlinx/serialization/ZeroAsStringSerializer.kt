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
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.reflect.KClass

/**
 * Class responsible for serializing the [Zero] type as [String].
 *
 * @constructor Creates an instance of [ZeroAsStringSerializer].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.primaryConstructor]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
public class ZeroAsStringSerializer : KSerializer<Zero> {
    /**
     * Describes the structure of the serializable representation of [Zero],
     * produced by this serializer.
     *
     * See the [KSerializer.descriptor] property for more details.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.descriptor]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "$this",
        PrimitiveKind.STRING
    )

    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [ZeroAsStringSerializer], or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is ZeroAsStringSerializer

    /**
     * Returns a hash code value for this serializer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    /**
     * Serializes the specified [value] using the format represented by the
     * specified [encoder].
     *
     * See the [KSerializer.serialize] function for more details.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.serialize]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun serialize(encoder: Encoder, value: Zero): Unit =
        encoder.encodeString("$value")

    /**
     * Deserializes the value of type [Zero] using the format represented by the
     * specified [decoder].
     *
     * See the [KSerializer.deserialize] function for more details.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.deserialize]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun deserialize(decoder: Decoder): Zero = decoder
        .decodeString()
        .let(Zero.Companion::orThrow)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this serializer, corresponding to
     * its [simple name][KClass.simpleName].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
