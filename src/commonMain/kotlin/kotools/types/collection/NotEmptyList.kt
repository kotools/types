package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotools.types.toSuccessfulResult

/**
 * Representation of lists that contain at least one element.
 *
 * @param E The type of elements contained in this list.
 */
@Serializable(NotEmptyListSerializer::class)
@SinceKotoolsTypes("4.0")
public class NotEmptyList<out E>
private constructor(private val elements: List<E>) : List<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptyList<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toList()
                ?.toSuccessfulResult(::NotEmptyList)
                ?: Result.failure(EmptyCollectionException)
    }

    override fun toString(): String = "$elements"
}

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return elements.toNotEmptyList()
        .getOrThrow()
}

/**
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * returns an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    NotEmptyList of this

internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    DelegatedSerializer<List<E>, NotEmptyList<E>> {
    override val delegate: KSerializer<List<E>> by lazy {
        ListSerializer(elementSerializer)
    }

    override val serialName: Result<NotBlankString> by lazy(
        "${Package.collection}.NotEmptyList"::toNotBlankString
    )

    override val deserializationException: IllegalArgumentException by lazy {
        EmptyCollectionException
    }

    override fun List<E>.toResultOfB(): Result<NotEmptyList<E>> =
        toNotEmptyList()
}
