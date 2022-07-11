package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

/**
 * Represents lists that can't be empty.
 *
 * @constructor Creates a [NotEmptyList] with a given [head] and an optional
 * [tail].
 */
@SinceKotoolsTypes("1.3")
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

    /**
     * Returns the element at the specified [index] in the list, or throws an
     * [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): E = get(index.value)

    /**
     * Returns the element at the specified [index] in the list, or throws an
     * [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: StrictlyPositiveInt): E =
        get(index.toPositiveInt())

    /**
     * Returns the element at the specified [index] in the list or `null` if the
     * [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): E? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Returns the element at the specified [index] in the list or `null` if the
     * [index] is out of bounds.
     */
    public infix fun getOrNull(index: StrictlyPositiveInt): E? =
        getOrNull(index.toPositiveInt())

    /**
     * Returns the element at the specified [index] in the list or the result of
     * calling the [defaultValue] function if the [index] is out of bounds.
     */
    public inline fun getOrElse(
        index: PositiveInt,
        defaultValue: (PositiveInt) -> @UnsafeVariance E
    ): E = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        defaultValue(index)
    }

    /**
     * Returns the element at the specified [index] in the list or the result of
     * calling the [defaultValue] function if the [index] is out of bounds.
     */
    public inline fun getOrElse(
        index: StrictlyPositiveInt,
        defaultValue: (StrictlyPositiveInt) -> @UnsafeVariance E
    ): E = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        defaultValue(index)
    }
}
