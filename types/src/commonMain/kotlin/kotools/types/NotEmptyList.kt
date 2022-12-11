package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * Representation of lists that contain at least one element.
 *
 * @param E The type of elements contained in this list.
 */
@SinceKotools(Types, "3.2")
public class NotEmptyList<out E>
private constructor(private val elements: List<E>) : List<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptyList<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toList()
                ?.toSuccessfulResult(::NotEmptyList)
                ?: Result.failure(
                    IllegalArgumentException(
                        "Given collection shouldn't be empty."
                    )
                )
    }

    override fun toString(): String = "$elements"
}

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotools(Types, "3.2")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return NotEmptyList.of(elements)
        .getOrThrow()
}

/**
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotools(Types, "3.2")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    NotEmptyList of this
