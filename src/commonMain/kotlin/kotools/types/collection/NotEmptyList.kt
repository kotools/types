package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.serializationError
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
 * println(integers) // [1, 2, 3]
 * ```
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return NotEmptyList.orThrow(elements)
}

/**
 * Returns an encapsulated [NotEmptyList] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * var collection: Collection<Int> = listOf(1, 2, 3)
 * var result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
 * println(result) // Success([1, 2, 3])
 *
 * collection = emptyList()
 * result = collection.toNotEmptyList()
 * println(result) // Failure(IllegalArgumentException)
 * ```
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptyList].
 *
 * ```kotlin
 * val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
 * val notEmptyList: NotEmptyList<Int> = original.toNotEmptyList()
 *     .getOrThrow()
 * println(original) // [1, 2, 3]
 * println(notEmptyList) // [1, 2, 3]
 *
 * original.clear()
 * println(original) // []
 * println(notEmptyList) // [1, 2, 3]
 * ```
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    runCatching {
        val elements: List<E> = toList()
        NotEmptyList.orThrow(elements)
    }

/**
 * Represents a list that has at least one element of type [E].
 *
 * You can use the [notEmptyListOf] or the [toNotEmptyList] functions for
 * building this type.
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NotEmptyListSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public value class NotEmptyList<out E> private constructor(
    private val elements: List<E>
) : NotEmptyCollection<E> {
    override val head: E get() = elements.first()

    override val tail: NotEmptyList<E>?
        get() = elements.drop(1)
            .toNotEmptyList()
            .getOrNull()

    init {
        val isValid: Boolean = elements.isNotEmpty()
        require(isValid) { ErrorMessage.emptyCollection }
    }

    /**
     * Returns all elements of this list as a [List] of type [E].
     *
     * Here's a simple usage example:
     *
     * ```kotlin
     * val notEmptyList: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
     * val list: List<Int> = notEmptyList.toList()
     * println(list) // [1, 2, 3]
     * ```
     */
    public fun toList(): List<E> = elements

    override fun toString(): String = "$elements"

    /** Contains static declarations for the [NotEmptyList] type. */
    public companion object {
        @JvmSynthetic
        internal fun <E> orThrow(elements: List<E>): NotEmptyList<E> =
            NotEmptyList(elements)
    }
}

@InternalKotoolsTypesApi
internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> by lazy {
        ListSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy { delegate.descriptor }

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>) {
        val elements: List<E> = value.toList()
        encoder.encodeSerializableValue(delegate, elements)
    }

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyList()
        .getOrNull()
        ?: serializationError(ErrorMessage.emptyCollection)
}
