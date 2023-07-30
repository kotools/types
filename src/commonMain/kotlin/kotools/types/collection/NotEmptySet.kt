package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 *
 * ```kotlin
 * import kotools.types.collection.NotEmptySet
 * import kotools.types.collection.notEmptySetOf
 *
 * val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
 * println(integers) // [1, 2, 3]
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val elements: Set<E> = setOf(head) + tail
    return NotEmptySet(elements)
}

/**
 * Returns an encapsulated [NotEmptySet] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 *
 * ```kotlin
 * import kotools.types.collection.NotEmptySet
 * import kotools.types.collection.toNotEmptySet
 *
 * var collection: Collection<Int> = setOf(1, 2, 3, 1)
 * var result: Result<NotEmptySet<Int>> = collection.toNotEmptySet()
 * println(result) // Success([1, 2, 3])
 *
 * collection = emptySet()
 * result = collection.toNotEmptySet()
 * println(result) // Failure(IllegalArgumentException)
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    runCatching {
        val elements: Set<E> = toSet()
        NotEmptySet(elements)
    }

/**
 * Represents a set with at least one element of type [E].
 *
 * You can use the [notEmptySetOf] or the [toNotEmptySet] functions for building
 * this type.
 */
@JvmInline
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotEmptySet<out E> internal constructor(
    private val elements: Set<E>
) : NotEmptyCollection<E> {
    override val head: E get() = elements.first()

    override val tail: NotEmptySet<E>?
        get() = elements.drop(1)
            .toNotEmptySet()
            .getOrNull()

    init {
        require(elements.isNotEmpty()) { EmptyCollectionException.message }
    }

    /**
     * Returns all elements of this set as a [Set] of type [E].
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptySet
     * import kotools.types.collection.notEmptySetOf
     *
     * val notEmptySet: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
     * val set: Set<Int> = notEmptySet.toSet()
     * println(set) // [1, 2, 3]
     * ```
     */
    public fun toSet(): Set<E> = elements

    override fun toString(): String = "$elements"
}

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> by lazy {
        SetSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy(delegate::descriptor)

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>): Unit =
        encoder.encodeSerializableValue(delegate, value.toSet())

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptySet()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
