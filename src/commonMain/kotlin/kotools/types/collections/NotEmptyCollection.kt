package kotools.types.collections

import kotools.types.core.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: Int,
    defaultValue: (Int) -> E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: PositiveInt,
    defaultValue: (PositiveInt) -> E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: StrictlyPositiveInt,
    defaultValue: (StrictlyPositiveInt) -> E
): E = getOrNull(index) ?: defaultValue(index)

/*
TODO: Make the NotEmptyCollection sealed when migrating collections for all
  platforms is done. This is a breaking change!
 */
/**
 * Parent of classes representing collections that contain at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
public interface NotEmptyCollection<out E> : Collection<E> {
    /** First element of this collection. */
    public val head: E

    // ---------- Query operations ----------

    /** Returns the [size] of this collection as a [StrictlyPositiveInt]. */
    public val typedSize: StrictlyPositiveInt get() = StrictlyPositiveInt(size)

    // ---------- Positional access operations ----------

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: Int): E

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): E = get(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: StrictlyPositiveInt): E =
        get(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: Int): E? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): E? = getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: StrictlyPositiveInt): E? =
        getOrNull(index.value)

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this collection as a
     * [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        toString().toNotBlankString()
}
