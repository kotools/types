package kotools.types.collection

import kotools.types.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.number.plus
import kotools.types.number.toStrictlyPositiveInt

/**
 * Representation of collections containing at least one element of type [E].
 */
@SinceKotoolsTypes("4.1")
public sealed interface NotEmptyCollection<out E> {
    /**
     * The first element of this collection.
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyCollection
     * import kotools.types.collection.notEmptyListOf
     *
     * val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
     * println(collection.head) // 1
     * ```
     */
    public val head: E

    /**
     * All elements of this collection except the [first one][head].
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyCollection
     * import kotools.types.collection.notEmptyListOf
     *
     * var collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
     * println(collection.tail) // [2, 3]
     *
     * collection = notEmptyListOf(-1)
     * println(collection.tail) // null
     * ```
     */
    public val tail: NotEmptyCollection<E>? get() = null

    /** The size of this collection. */
    public val size: StrictlyPositiveInt
        get() = tail.sizeOrZero.plus(1)
            .toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Returns the string representation of this collection.
     *
     * Implementations of this function should behave like calling the
     * [Any.toString] function on an [Iterable].
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyCollection
     * import kotools.types.collection.notEmptyListOf
     *
     * val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
     * println(collection) // [1, 2, 3]
     *
     * val list: Iterable<Int> = listOf(1, 2, 3)
     * println("$collection" == "$list") // true
     * ```
     */
    override fun toString(): String
}

/**
 * Returns the size of this collection, or returns [ZeroInt] if this collection
 * is `null`.
 */
@SinceKotoolsTypes("4.1")
public val NotEmptyCollection<*>?.sizeOrZero: PositiveInt
    get() = this?.size ?: ZeroInt

internal object EmptyCollectionException :
    IllegalArgumentException("Given collection shouldn't be empty.")
