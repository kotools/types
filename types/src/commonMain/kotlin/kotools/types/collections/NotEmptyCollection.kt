package kotools.types.collections

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.strictlyPositiveIntOrThrow
import kotools.types.number.toPositiveIntOrNull
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankStringOrThrow
import kotools.types.tryOrNull

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@Deprecated(
    "The index should be a PositiveInt or a StrictlyPositiveInt.",
    ReplaceWith(
        "this.getOrElse(PositiveInt(index), defaultValue)",
        "${Package.number}.PositiveInt"
    )
)
@SinceKotools(Types, "1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: Int,
    defaultValue: (Int) -> E
): E = index.toPositiveIntOrNull()
    ?.let(::getOrNull)
    ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotools(Types, "1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: PositiveInt,
    defaultValue: (PositiveInt) -> E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Parent of classes representing collections that contain at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotools(Types, "1.3")
public sealed interface NotEmptyCollection<out E> : Collection<E> {
    /** First element of this collection. */
    public val head: E

    // ---------- Query operations ----------

    /** Returns the [size] of this collection as a [StrictlyPositiveInt]. */
    public val typedSize: StrictlyPositiveInt
        get() = strictlyPositiveIntOrThrow(size)

    // ---------- Positional access operations ----------

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Deprecated(
        "The index should be a PositiveInt.",
        ReplaceWith(
            "this[PositiveInt(index)]",
            "${Package.number}.PositiveInt"
        )
    )
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: Int): E

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @SinceKotools(Types, "1.3")
    @Throws(IndexOutOfBoundsException::class)
    public operator fun get(index: PositiveInt): E = elementAt(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    @Deprecated(
        "The index should be a PositiveInt or a StrictlyPositiveInt.",
        ReplaceWith(
            "getOrNull(PositiveInt(index))",
            "${Package.number}.PositiveInt"
        )
    )
    public infix fun getOrNull(index: Int): E? = index.toPositiveIntOrNull()
        ?.let(::getOrNull)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    @SinceKotools(Types, "1.3")
    public infix fun getOrNull(index: PositiveInt): E? =
        tryOrNull { get(index) }

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this collection as a
     * [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString = toString()
        .toNotBlankStringOrThrow()
}
