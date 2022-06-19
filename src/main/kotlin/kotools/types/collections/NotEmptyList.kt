package kotools.types.collections

/** Creates a [NotEmptyList] with a given [head] and an optional [tail]. */
public fun <E : Any> notEmptyList(head: E, vararg tail: E): NotEmptyList<E> =
    NotEmptyList.create(head, *tail)

/** Type representing lists that can't be empty. */
public class NotEmptyList<out E : Any> private constructor(
    /** First element of the current list. */
    public val head: E,
    private val tail: List<E>
) : AbstractList<E>() {
    override val size: Int get() = tail.size + 1

    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]

    override fun isEmpty(): Boolean = false

    internal companion object {
        fun <E : Any> create(head: E, vararg tail: E): NotEmptyList<E> =
            NotEmptyList(head, tail.asList())
    }
}
