package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Represents mutable collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
public sealed interface NotEmptyMutableCollection<E> : MutableCollection<E>,
    NotEmptyCollection<E> {
    /**
     * Inserts the [element] into this list at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun add(index: Int, element: E)

    /**
     * Removes an element at the specified [index] from the list, or throws an
     * [IndexOutOfBoundsException] if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix fun removeAt(index: Int): E

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or throws an [IndexOutOfBoundsException] if the
     * [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun set(index: Int, element: E): E
}
