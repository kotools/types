package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.serializationError
import org.kotools.types.internal.InternalKotoolsTypesApi
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [kotools.types.collection.NotEmptySetKtCommonSample.notEmptySetOf]
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val elements: Set<E> = setOf(head) + tail
    return NotEmptySet.orThrow(elements)
}

/**
 * Returns an encapsulated [NotEmptySet] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [kotools.types.collection.NotEmptySetKtCommonSample.toNotEmptySetOnCollection]
 * </details>
 * <br>
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptySet].
 *
 * SAMPLE: [kotools.types.collection.NotEmptySetKtCommonSample.toNotEmptySetOnMutableCollection]
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    runCatching {
        val elements: Set<E> = toSet()
        NotEmptySet.orThrow(elements)
    }

/**
 * Represents a set that has at least one element of type [E].
 *
 * You can use the [notEmptySetOf] or the [toNotEmptySet] functions for creating
 * an instance of this type.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [Set] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * SAMPLE: [kotools.types.collection.NotEmptySetCommonSample.serialization]
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NotEmptySetSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public value class NotEmptySet<out E> private constructor(
    private val elements: Set<E>
) : NotEmptyCollection<E> {
    /** Contains static declarations for the [NotEmptySet] type. */
    public companion object {
        @InternalKotoolsTypesApi
        @JvmSynthetic
        internal fun <E> orThrow(elements: Set<E>): NotEmptySet<E> {
            val isValid: Boolean = elements.isNotEmpty()
            require(isValid) { ErrorMessage.emptyCollection }
            return NotEmptySet(elements)
        }
    }

    override val head: E get() = elements.first()

    override val tail: NotEmptySet<E>?
        get() = elements.drop(1)
            .toNotEmptySet()
            .getOrNull()

    /**
     * Returns all elements of this set as a [Set] of type [E].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [kotools.types.collection.NotEmptySetCommonSample.toSet]
     * </details>
     */
    public fun toSet(): Set<E> = elements

    override fun toString(): String = "$elements"
}

@InternalKotoolsTypesApi
internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> by lazy {
        SetSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy(delegate::descriptor)

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>) {
        val elements: Set<E> = value.toSet()
        encoder.encodeSerializableValue(delegate, elements)
    }

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptySet()
        .getOrElse { serializationError(ErrorMessage.emptyCollection) }
}
