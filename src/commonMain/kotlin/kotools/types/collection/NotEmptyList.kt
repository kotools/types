package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

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

/** Representation of lists that contain at least one element of type [E]. */
@JvmInline
@Serializable(NotEmptyListSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotEmptyList<out E> private constructor(
    private val elements: List<E>
) : NotEmptyCollection<E> {
    override val head: E get() = elements.first()
    override val tail: NotEmptyList<E>?
        get() = elements.drop(1)
            .toNotEmptyList()
            .getOrNull()

    internal constructor(head: E, tail: NotEmptyList<E>? = null) : this(
        tail?.run { listOf(head) + elements }
            ?: listOf(head)
    )

    /** Returns all elements of this list as a [List] of type [E]. */
    public fun toList(): List<E> {
        val firstElement: List<E> = listOf(head)
        return tail?.let { firstElement + it.toList() } ?: firstElement
    }

    override fun toString(): String = "${toList()}"
}

internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> by lazy {
        ListSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy(delegate::descriptor)

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>): Unit =
        encoder.encodeSerializableValue(delegate, value.toList())

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyList()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
