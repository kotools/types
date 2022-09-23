package kotools.types.collections

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns a not empty list containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Collection<E>.toNotEmptyList(): NotEmptyList<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val list: List<E> = toList()
    return NotEmptyList(list)
}

/**
 * Returns a not empty list containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Array<E>.toNotEmptyList(): NotEmptyList<E> =
    toList().toNotEmptyList()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Collection<E>.toNotEmptyListOrNull(): NotEmptyList<E>? = try {
    toNotEmptyList()
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Array<E>.toNotEmptyListOrNull(): NotEmptyList<E>? =
    toList().toNotEmptyListOrNull()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <E> Collection<E>.toNotEmptyListOrElse(
    defaultValue: (Collection<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns the result of calling the [defaultValue] function if this array is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <E> Array<E>.toNotEmptyListOrElse(
    defaultValue: (Array<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Represents lists containing at least one element.
 *
 * @param E The type of elements contained in this list.
 *
 * @constructor Creates a not empty list starting with a [head] and containing
 * all the elements of the optional [tail].
 */
@Serializable(NotEmptyList.Serializer::class)
@SinceKotoolsTypes("1.3")
public class NotEmptyList<out E> internal constructor(
    override val head: E,
    private val tail: List<E>
) : AbstractList<E>(), NotEmptyCollection<E> {
    public constructor(head: E, vararg tail: E) : this(head, tail.toList())

    internal constructor(list: List<E>) : this(
        list.first(),
        list.subList(1, list.size)
    )

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyCollection>.isEmpty()

    // ---------- Positional access operations ----------

    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]

    @SinceKotoolsTypes("3.0")
    internal class Serializer<E>(elementSerializer: KSerializer<E>) :
        SealedNotEmptyCollectionSerializer<E, NotEmptyList<E>>(
            elementSerializer,
            Collection<E>::toNotEmptyList
        )
}
