package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.toSuccessfulResult

/** Representation of lists that contain at least one element of type [E]. */
@Serializable(NotEmptyListSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptyList<out E> internal constructor(
    /** The first element of this list. */
    public val head: E,
    /** All elements of this list except [the first one][head]. */
    public val tail: NotEmptyList<E>? = null
) {
    /** The size of this list. */
    public val size: StrictlyPositiveInt by lazy(
        toList().size.toStrictlyPositiveInt()::getOrThrow
    )

    /** Returns all elements of this list as a [List] of type [E]. */
    public fun toList(): List<E> {
        val firstElement: List<E> = listOf(head)
        return tail?.let { firstElement + it.toList() } ?: firstElement
    }

    /** Returns the string representation of this list. */
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
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * returns an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    takeIf(Collection<E>::isNotEmpty)
        ?.toSuccessfulResult {
            val head: E = it.first()
            val tail: NotEmptyList<E>? = it.drop(1)
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

    @ExperimentalSerializationApi
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
