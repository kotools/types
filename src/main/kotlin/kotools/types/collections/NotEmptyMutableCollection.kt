package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

/**
 * Represents mutable collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
public sealed interface NotEmptyMutableCollection<E> : MutableCollection<E>,
    NotEmptyCollection<E> {
    // ---------- Positional Access Operations ----------

    /**
     * Inserts the [element] into this list at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun add(index: Int, element: E)

    /**
     * Inserts the [element] into this list at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun add(index: PositiveInt, element: E): Unit =
        add(index.value, element)

    /**
     * Inserts the [element] into this list at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun add(index: StrictlyPositiveInt, element: E): Unit =
        add(index.value, element)

    /**
     * Inserts the [element] into this list at the specified [index], or returns
     * `null` if the [index] is out of bounds.
     */
    public fun addOrNull(index: Int, element: E): Unit? = try {
        add(index, element)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Inserts the [element] into this list at the specified [index], or returns
     * `null` if the [index] is out of bounds.
     */
    public fun addOrNull(index: PositiveInt, element: E): Unit? =
        addOrNull(index.value, element)

    /**
     * Inserts the [element] into this list at the specified [index], or returns
     * `null` if the [index] is out of bounds.
     */
    public fun addOrNull(index: StrictlyPositiveInt, element: E): Unit? =
        addOrNull(index.value, element)

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
     * Removes an element at the specified [index] from the list, or throws an
     * [IndexOutOfBoundsException] if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix fun removeAt(index: PositiveInt): E = removeAt(index.value)

    /**
     * Removes an element at the specified [index] from the list, or throws an
     * [IndexOutOfBoundsException] if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix fun removeAt(index: StrictlyPositiveInt): E =
        removeAt(index.value)

    /**
     * Removes an element at the specified [index] from the list, or returns
     * `null` if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    public infix fun removeAtOrNull(index: Int): E? = try {
        removeAt(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Removes an element at the specified [index] from the list, or returns
     * `null` if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    public infix fun removeAtOrNull(index: PositiveInt): E? =
        removeAtOrNull(index.value)

    /**
     * Removes an element at the specified [index] from the list, or returns
     * `null` if the index is out of bounds.
     *
     * Because this list shouldn't be empty, the element will not be removed if
     * this list contains only one element.
     */
    public infix fun removeAtOrNull(index: StrictlyPositiveInt): E? =
        removeAtOrNull(index.value)

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or throws an [IndexOutOfBoundsException] if the
     * [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public fun set(index: Int, element: E): E
}
