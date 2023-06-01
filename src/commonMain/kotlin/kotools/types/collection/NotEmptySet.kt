package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes

/** Representation of sets that contain at least one element of type [E]. */
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptySet<out E> internal constructor(
    /** The first element of this set. */
    override val head: E,
    /** All elements of this set except [the first one][head]. */
    override val tail: NotEmptySet<E>? = null
) : NotEmptyCollection<E> {
    /** Returns all elements of this set as a [Set] of type [E]. */
    public fun toSet(): Set<E> {
        val elements: Set<E> = setOf(head)
        return tail?.let { elements + it.toSet() } ?: elements
    }

    /** Returns the string representation of this set. */
    override fun toString(): String = "${toSet()}"
}

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> =
    setOf(head, *tail)
        .toNotEmptySet()
        .getOrThrow()

/**
 * Returns an encapsulated [NotEmptySet] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    takeIf(Collection<E>::isNotEmpty)
        ?.runCatching {
            val head: E = first()
            val tail: NotEmptySet<E>? = drop(1)
                .toNotEmptySet()
                .getOrNull()
            NotEmptySet(head, tail)
        }
        ?: Result.failure(EmptyCollectionException)

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
