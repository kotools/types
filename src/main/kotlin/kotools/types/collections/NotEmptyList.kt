package kotools.types.collections

import kotools.types.number.StrictlyPositiveInt

/**
 * Represents lists that can't be empty.
 *
 * @constructor Creates a [NotEmptyList] with a given [head] and an optional
 * [tail].
 */
public class NotEmptyList<out E>(
    /** First element of the current list. */
    public val head: E,
    vararg tail: E
) : AbstractList<E>() {
    private val tail: List<E>

    init {
        this.tail = tail.toList()
    }

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + 1

    /** Returns the [size] of the collection as a [StrictlyPositiveInt]. */
    public val typedSize: StrictlyPositiveInt get() = StrictlyPositiveInt(size)

    override fun isEmpty(): Boolean = false

    // ---------- Positional Access Operations ----------

    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]
}
