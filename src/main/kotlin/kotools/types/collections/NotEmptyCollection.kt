package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt

/**
 * Represents collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
internal sealed interface NotEmptyCollection<out E> : Collection<E> {
    /** First element of this collection. */
    val head: E

    // ---------- Query operations ----------

    override val size: Int get() = 1

    /** Returns the [size] of this collection as a strictly positive int. */
    val typedSize: StrictlyPositiveInt get() = StrictlyPositiveInt(size)

    override fun isEmpty(): Boolean = false

    // ---------- Positional Access Operations ----------

    /**
     * Returns the element at the specified [index] in the list, or throws an
     * [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    infix operator fun get(index: Int): E
}
