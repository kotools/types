package kotools.types.collections

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotools.types.annotations.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns a not empty set containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Array<E>.toNotEmptySet(): NotEmptySet<E> =
    toSet().toNotEmptySet()

/**
 * Returns a not empty set containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Collection<E>.toNotEmptySet(): NotEmptySet<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val head: E = first()
    val set: MutableSet<E> = mutableSetOf()
    for (index in 1 until size) set += elementAt(index)
    val tail: Array<E> = set.toTypedArray()
    return NotEmptySet(head, *tail)
}

/**
 * Returns a not empty set containing all the elements of this array, or returns
 * `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Array<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    toSet().toNotEmptySetOrNull()

/**
 * Returns a not empty set containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptySetOrNull():
        NotEmptySet<E>? = try {
    toNotEmptySet()
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns a not empty set containing all the elements of this array, or returns
 * the result of calling the [defaultValue] function if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Array<E>.toNotEmptySetOrElse(
    defaultValue: (Array<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Returns a not empty set containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Collection<E>.toNotEmptySetOrElse(
    defaultValue: (Collection<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Represents sets containing at least one element.
 *
 * @param E The type of elements contained in this set.
 *
 * @constructor Creates a not empty set starting with a [head] and containing
 * all the elements of the optional [tail].
 */
@Serializable(NotEmptySet.Serializer::class)
@SinceKotoolsTypes("1.3")
public class NotEmptySet<out E> private constructor(
    override val head: E,
    private val tail: Set<E>
) : AbstractSet<E>(), NotEmptyCollection<E> {
    public constructor(head: E, vararg tail: E) : this(
        head,
        tail.filterNot { it == head }.toSet()
    )

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyCollection>.isEmpty()

    override fun iterator(): Iterator<E> = mutableSetOf(head).run {
        this += tail
        toSet().iterator()
    }

    // ---------- Positional access operations ----------

    override fun get(index: Int): E = if (index == 0) head
    else tail.elementAt(index - 1)

    @SinceKotoolsTypes("2.1")
    internal class Serializer<E>(elementSerializer: KSerializer<E>) :
        NotEmptyCollectionSerializer<E, NotEmptySet<E>>(
            elementSerializer,
            { head: E, tail: Collection<E> ->
                val set: Set<E> = tail.filterNot { it == head }.toSet()
                NotEmptySet(head, set)
            }
        )
}
