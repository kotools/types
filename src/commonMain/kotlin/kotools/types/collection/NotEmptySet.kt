package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.toSuccessfulResult

/**
 * Representation of sets that contain at least one element of type [E].
 *
 * See the [notEmptySetOf] function or the [asNotEmptySet] property for building
 * a [NotEmptySet].
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptySet<out E> internal constructor(
    /** The first element of this set. */
    public val head: E,
    /** All elements of this set except [the first one][head]. */
    public val tail: NotEmptySet<E>? = null
) {
    /** The size of this set. */
    public val size: StrictlyPositiveInt by lazy(
        toSet().size.toStrictlyPositiveInt()::getOrThrow
    )

    /** Returns all elements of this set as a [Set] of type [E]. */
    public fun toSet(): Set<E> {
        val elements: Set<E> = setOf(head)
        return tail?.let { elements + it.toSet() } ?: elements
    }

    /** Returns the string representation of this set. */
    override fun toString(): String = "${toSet()}"
}

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("4.0")
public val <E> Collection<E>.asNotEmptySet: Result<NotEmptySet<E>>
    get() = if (isEmpty()) Result.failure(EmptyCollectionException)
    else toSuccessfulResult {
        NotEmptySet(head = first(), tail = drop(1).asNotEmptySet.getOrNull())
    }

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> =
    setOf(head, *tail).asNotEmptySet.getOrThrow()

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> by lazy {
        SetSerializer(elementSerializer)
    }

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor by lazy {
        SerialDescriptor(
            "${Package.collection}.NotEmptySet",
            delegate.descriptor
        )
    }

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>): Unit =
        encoder.encodeSerializableValue(delegate, value.toSet())

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = decoder
        .decodeSerializableValue(delegate)
        .asNotEmptySet
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
