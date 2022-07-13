package kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

// ---------- Conversions ----------

/**
 * Returns this value as a [NotBlankString], or throws an
 * [IllegalArgumentException] if it's blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/** Returns this value as a [NotBlankString] or `null` if it's blank. */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    NotBlankString orNull this

/**
 * Represents strings that can't be empty and doesn't contain only whitespace
 * characters.
 *
 * @constructor Returns the [value] as a [NotBlankString], or throws an
 * [IllegalArgumentException] if it's blank.
 */
@JvmInline
@SinceKotoolsTypes("1.2")
public value class NotBlankString(
    public val value: String
) : Comparable<NotBlankString> {
    init {
        require(value.isNotBlank()) { "Given value shouldn't be blank." }
    }

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

    // ---------- Positional Access Operations ----------

    /** Returns the first character of this [value]. */
    public val first: Char get() = value[0]

    /**
     * Returns the character of this [value] at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): Char =
        value[index.value]

    /**
     * Returns the character of this [value] at the specified [index] or `null`
     * if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): Char? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    // ---------- Binary operations ----------

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the given [other] object.
     */
    public infix operator fun plus(other: Any?): NotBlankString =
        NotBlankString(value + other)

    // ---------- Comparisons ----------

    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    // ---------- Conversions ----------

    override fun toString(): String = value

    public companion object {
        /**
         * Returns the [value] as a [NotBlankString] or `null` if it's blank.
         */
        public infix fun orNull(value: String): NotBlankString? = try {
            NotBlankString(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
