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
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or throws an [IndexOutOfBoundsException] if the
     * [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public operator fun set(index: Int, element: E): E

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or throws an [IndexOutOfBoundsException] if the
     * [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public operator fun set(index: PositiveInt, element: E): E =
        set(index.value, element)

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or throws an [IndexOutOfBoundsException] if the
     * [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public operator fun set(index: StrictlyPositiveInt, element: E): E =
        set(index.value, element)

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or returns `null` if the [index] is out of bounds.
     */
    public fun setOrNull(index: Int, element: E): E? = try {
        set(index, element)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or returns `null` if the [index] is out of bounds.
     */
    public fun setOrNull(index: PositiveInt, element: E): E? =
        setOrNull(index.value, element)

    /**
     * Replaces the element at the specified [index] in this list with the
     * specified [element], or returns `null` if the [index] is out of bounds.
     */
    public fun setOrNull(index: StrictlyPositiveInt, element: E): E? =
        setOrNull(index.value, element)
}
