package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns a not empty mutable list containing all the elements of this array,
 * or throws an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Array<E>.toNotEmptyMutableList(): NotEmptyMutableList<E> =
    toList().toNotEmptyMutableList()

/**
 * Returns a not empty mutable list containing all the elements of this
 * collection, or throws an [IllegalArgumentException] if this collection is
 * empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Collection<E>.toNotEmptyMutableList(): NotEmptyMutableList<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val head: E = first()
    val list: MutableList<E> = mutableListOf()
    for (index in 1 until size) list += elementAt(index)
    val tail: Array<E> = list.toTypedArray()
    return NotEmptyMutableList(head, *tail)
}

/**
 * Returns a not empty mutable list containing all the elements of this array,
 * or returns `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Array<E>.toNotEmptyMutableListOrNull(): NotEmptyMutableList<E>? =
    toList().toNotEmptyMutableListOrNull()

/**
 * Returns a not empty mutable list containing all the elements of this
 * collection, or returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyMutableListOrNull(): NotEmptyMutableList<E>? =
    try {
        toNotEmptyMutableList()
    } catch (_: IllegalArgumentException) {
        null
    }

/**
 * Returns a not empty mutable list containing all the elements of this array,
 * or returns the result of calling the [defaultValue] function if this array is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Array<E>.toNotEmptyMutableListOrElse(
    defaultValue: (Array<E>) -> NotEmptyMutableList<E>
): NotEmptyMutableList<E> = toNotEmptyMutableListOrNull() ?: defaultValue(this)

/**
 * Returns a not empty mutable list containing all the elements of this
 * collection, or returns the result of calling the [defaultValue] function if
 * this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Collection<E>.toNotEmptyMutableListOrElse(
    defaultValue: (Collection<E>) -> NotEmptyMutableList<E>
): NotEmptyMutableList<E> = toNotEmptyMutableListOrNull() ?: defaultValue(this)

/**
 * Represents mutable lists containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 *
 * @constructor Creates a not empty mutable list starting with a [head] and
 * containing all the elements of the optional [tail].
 */
@SinceKotoolsTypes("1.3")
public class NotEmptyMutableList<E>(override val head: E, vararg tail: E) :
    AbstractMutableList<E>(),
    NotEmptyMutableCollection<E> {
    private val tail: List<E>

    init {
        this.tail = tail.toList()
    }

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyMutableCollection>.isEmpty()

    // ---------- Positional Access Operations ----------

    override fun add(index: Int, element: E): Unit = TODO("Not implemented yet")
    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]
    override fun removeAt(index: Int): E = TODO("Not implemented yet")
    override fun set(index: Int, element: E): E = TODO("Not implemented yet")
}
