package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * Representation of sets that contain at least one element.
 *
 * @param E The type of elements contained in this set.
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotools(Types, "3.2")
public class NotEmptySet<out E>
private constructor(private val elements: Set<E>) : Set<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptySet<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toSet()
                ?.toSuccessfulResult(::NotEmptySet)
                ?: Result.failure(EmptyCollectionError)
    }

    override fun toString(): String = "$elements"
}

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    Serializer<NotEmptySet<E>, Set<E>>(
        delegate = SetSerializer(elementSerializer),
        toDelegatedType = { it },
        toType = Set<E>::toNotEmptySet
    )

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotools(Types, "3.2")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val result: List<E> = listOf(head) + tail
    return result.toNotEmptySet()
        .getOrThrow()
}

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotools(Types, "3.2")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    NotEmptySet of this
