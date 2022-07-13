package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

// ---------- Conversions ----------

// TODO: Add conversions from Array to NotEmptyList

/**
 * Returns this [Collection] as a [NotEmptyList], or throws an
 * [IllegalArgumentException] if this collection is empty.
 */
@Throws(IllegalArgumentException::class)
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyList(): NotEmptyList<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val list: MutableList<E> = mutableListOf()
    forEach { list += it }
    val head: E = list.removeFirst()
    val tail: Array<E> = list.toTypedArray()
    return NotEmptyList(head, *tail)
}

/**
 * Returns this [Collection] as a [NotEmptyList] or `null` if this collection is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyListOrNull(): NotEmptyList<E>? =
    try {
        toNotEmptyList()
    } catch (_: IllegalArgumentException) {
        null
    }

/**
 * Returns this [Collection] as a [NotEmptyList] or the result of calling the
 * [defaultValue] function if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyListOrElse(
    defaultValue: (Collection<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

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
    public infix fun getOrNull(index: PositiveInt): E? = getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in the list or `null` if the
     * [index] is out of bounds.
     */
    public infix fun getOrNull(index: StrictlyPositiveInt): E? =
        getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in the list or the result of
     * calling the [defaultValue] function if the [index] is out of bounds.
     */
    public inline fun getOrElse(
        index: PositiveInt,
        defaultValue: (PositiveInt) -> @UnsafeVariance E
    ): E = getOrNull(index) ?: defaultValue(index)

    /**
     * Returns the element at the specified [index] in the list or the result of
     * calling the [defaultValue] function if the [index] is out of bounds.
     */
    public inline fun getOrElse(
        index: StrictlyPositiveInt,
        defaultValue: (StrictlyPositiveInt) -> @UnsafeVariance E
    ): E = getOrNull(index) ?: defaultValue(index)
}
