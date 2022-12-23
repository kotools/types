package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotools.types.toSuccessfulResult

/**
 * Representation of sets that contain at least one element.
 *
 * @param E The type of elements contained in this set.
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("0.1")
public class NotEmptySet<out E>
private constructor(private val elements: Set<E>) : Set<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptySet<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toSet()
                ?.toSuccessfulResult(::NotEmptySet)
                ?: Result.failure(EmptyCollectionException)
    }

    override fun toString(): String = "$elements"
}

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("0.1")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val result: List<E> = listOf(head) + tail
    return result.toNotEmptySet()
        .getOrThrow()
}

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("0.1")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    NotEmptySet of this

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    DelegatedSerializer<Set<E>, NotEmptySet<E>> {
    override val delegate: KSerializer<Set<E>> by lazy {
        SetSerializer(elementSerializer)
    }

    override val serialName: Result<NotBlankString> by lazy(
        "${Package.collection}.NotEmptySet"::toNotBlankString
    )

    override val deserializationException: IllegalArgumentException by lazy {
        EmptyCollectionException
    }

    override fun Set<E>.toResultOfB(): Result<NotEmptySet<E>> = toNotEmptySet()
}
