package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * Representation of lists that contain at least one element.
 *
 * @param E The type of elements contained in this list.
 */
@Serializable(NotEmptyListSerializer::class)
@SinceKotools(Types, "3.2")
public class NotEmptyList<out E>
private constructor(private val elements: List<E>) : List<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptyList<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toList()
                ?.toSuccessfulResult(::NotEmptyList)
                ?: Result.failure(EmptyCollectionError)
    }

    override fun toString(): String = "$elements"
}

internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> =
        ListSerializer(elementSerializer)

    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>): Unit =
        delegate.serialize(encoder, value)

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = delegate
        .deserialize(decoder)
        .toNotEmptyList()
        .getOrThrow()
}

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotools(Types, "3.2")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return elements.toNotEmptyList()
        .getOrThrow()
}

/**
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotools(Types, "3.2")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    NotEmptyList of this
