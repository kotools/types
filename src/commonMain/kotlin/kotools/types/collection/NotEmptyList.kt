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
import kotools.types.number.asStrictlyPositiveInt
import kotools.types.toSuccessfulResult

/**
 * Representation of lists that contain at least one element of type [E].
 *
 * See the [notEmptyListOf] function or the [asNotEmptyList] property for
 * building a [NotEmptyList].
 */
@Serializable(NotEmptyListSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptyList<out E> internal constructor(
    /** The first element of this list. */
    public val head: E,
    /** All elements of this list except [the first one][head]. */
    public val tail: NotEmptyList<E>? = null
) {
    /** Returns all elements of this list as a [List] of type [E]. */
    public val asList: List<E> by lazy {
        val firstElement: List<E> = listOf(head)
        tail?.let { firstElement + it.asList } ?: firstElement
    }

    /** The size of this list. */
    public val size: StrictlyPositiveInt by lazy(
        asList.size.asStrictlyPositiveInt::getOrThrow
    )

    /** Returns the string representation of this list. */
    override fun toString(): String = "$asList"
}

/**
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * returns an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("4.0")
public val <E> Collection<E>.asNotEmptyList: Result<NotEmptyList<E>>
    get() = if (isEmpty()) Result.failure(EmptyCollectionException)
    else toSuccessfulResult {
        NotEmptyList(head = first(), tail = drop(1).asNotEmptyList.getOrNull())
    }

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> = tail
    .takeIf(Array<out E>::isNotEmpty)
    ?.toList()
    ?.asNotEmptyList
    ?.getOrNull()
    .let { NotEmptyList(head, it) }

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
        encoder.encodeSerializableValue(delegate, value.asList)

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .asNotEmptyList
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
