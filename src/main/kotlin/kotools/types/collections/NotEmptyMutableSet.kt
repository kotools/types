package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns a not empty mutable set containing all the elements of this array, or
 * throws an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Array<E>.toNotEmptyMutableSet():
        NotEmptyMutableSet<E> = toSet().toNotEmptyMutableSet()

/**
 * Returns a not empty mutable set containing all the elements of this
 * collection, or throws an [IllegalArgumentException] if this collection is
 * empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Collection<E>.toNotEmptyMutableSet():
        NotEmptyMutableSet<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val head: E = first()
    val set: MutableSet<E> = mutableSetOf()
    for (index in 1 until size) set += elementAt(index)
    val tail: Array<E> = set.toTypedArray()
    return NotEmptyMutableSet(head, *tail)
}

/**
 * Returns a not empty mutable set containing all the elements of this array, or
 * returns `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Array<E>.toNotEmptyMutableSetOrNull():
        NotEmptyMutableSet<E>? = toSet().toNotEmptyMutableSetOrNull()

/**
 * Returns a not empty mutable set containing all the elements of this
 * collection, or returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyMutableSetOrNull():
        NotEmptyMutableSet<E>? = try {
    toNotEmptyMutableSet()
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns a not empty mutable set containing all the elements of this array, or
 * returns the result of calling the [defaultValue] function if this array is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Array<E>.toNotEmptyMutableSetOrElse(
    defaultValue: (Array<E>) -> NotEmptyMutableSet<E>
): NotEmptyMutableSet<E> = toNotEmptyMutableSetOrNull() ?: defaultValue(this)

/**
 * Returns a not empty mutable set containing all the elements of this
 * collection, or returns the result of calling the [defaultValue] function if
 * this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Collection<E>.toNotEmptyMutableSetOrElse(
    defaultValue: (Collection<E>) -> NotEmptyMutableSet<E>
): NotEmptyMutableSet<E> = toNotEmptyMutableSetOrNull() ?: defaultValue(this)

/**
 * Represents mutable sets containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 *
 * @constructor Creates a not empty mutable set starting with a [head] and
 * containing all the elements of the optional [tail].
 */
@SinceKotoolsTypes("1.3")
public class NotEmptyMutableSet<E>(override var head: E, vararg tail: E) :
    AbstractMutableSet<E>(),
    NotEmptyCollection<E> {
    private val tail: MutableSet<E>

    init {
        this.tail = tail.filterNot { it == head }.toMutableSet()
    }

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyCollection>.isEmpty()

    override fun iterator(): MutableIterator<E> = mutableSetOf(head)
        .apply { this += tail }
        .iterator()

    // ---------- Positional access operations ----------

    override fun get(index: Int): E = if (index == 0) head
    else tail.elementAt(index - 1)

    // ---------- Modification operations ----------

    override infix fun add(element: E): Boolean = if (element in this) false
    else tail.add(element)

    override infix fun remove(element: E): Boolean =
        if (size == 1 || element !in this) false
        else if (element == head) {
            head = tail.first().also(tail::remove)
            true
        } else tail.remove(element)
}
