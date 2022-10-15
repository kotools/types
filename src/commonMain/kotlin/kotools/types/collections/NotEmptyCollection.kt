package kotools.types.collections

import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotools.types.tryOrNull
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@Deprecated(
    "The index should be a PositiveInt or a StrictlyPositiveInt.",
    ReplaceWith(
        "this.getOrElse(PositiveInt(index), defaultValue)",
        "kotools.types.number.PositiveInt"
    )
)
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
@Suppress("DEPRECATION")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: Int,
    defaultValue: (Int) -> E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: PositiveInt,
    defaultValue: (PositiveInt) -> E
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
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
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
    @Deprecated(
        "The index should be a PositiveInt or a StrictlyPositiveInt.",
        ReplaceWith(
            "get(PositiveInt(index))",
            "kotools.types.number.PositiveInt"
        )
    )
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: Int): E

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
    @Throws(IndexOutOfBoundsException::class)
    public operator fun get(index: PositiveInt): E =
        elementAt(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    @Deprecated(
        "The index should be a PositiveInt or a StrictlyPositiveInt.",
        ReplaceWith(
            "getOrNull(PositiveInt(index))",
            "kotools.types.number.PositiveInt"
        )
    )
    @Suppress("DEPRECATION")
    public infix fun getOrNull(index: Int): E? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    @SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
    public infix fun getOrNull(index: PositiveInt): E? =
        tryOrNull { get(index) }

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this collection as a
     * [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        toString().toNotBlankString()
}
