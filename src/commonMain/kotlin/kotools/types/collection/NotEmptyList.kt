package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes

/** Representation of lists that contain at least one element of type [E]. */
@Serializable(NotEmptyListSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptyList<out E> internal constructor(
    override val head: E,
    override val tail: NotEmptyList<E>? = null
) : NotEmptyCollection<E> {
    /** Returns all elements of this list as a [List] of type [E]. */
    public fun toList(): List<E> {
        val firstElement: List<E> = listOf(head)
        return tail?.let { firstElement + it.toList() } ?: firstElement
    }

    override fun toString(): String = "${toList()}"
}

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> = tail
    .takeIf(Array<out E>::isNotEmpty)
    ?.toList()
    ?.toNotEmptyList()
    ?.getOrNull()
    .let { NotEmptyList(head, it) }

/**
 * Returns an encapsulated [NotEmptyList] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    takeIf(Collection<E>::isNotEmpty)
        ?.runCatching {
            val head: E = first()
            val tail: NotEmptyList<E>? = drop(1)
                .toNotEmptyList()
                .getOrNull()
            NotEmptyList(head, tail)
        }
        ?: Result.failure(EmptyCollectionException)

internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> by lazy {
        ListSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy {
        SerialDescriptor(
            "${Package.collection}.NotEmptyList",
            delegate.descriptor
        )
    }

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>): Unit =
        encoder.encodeSerializableValue(delegate, value.toList())

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyList()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
