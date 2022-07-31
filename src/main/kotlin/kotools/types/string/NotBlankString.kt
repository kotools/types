package kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

/**
 * Returns this value as a not blank string, or throws an
 * [IllegalArgumentException] if this value is blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/**
 * Returns this value as a not blank string, or returns `null` if this value is
 * blank.
 */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    NotBlankString orNull this

/**
 * Represents strings containing at least 1 character, excluding whitespaces.
 *
 * @constructor Returns the [value] as a not blank string, or throws an
 * [IllegalArgumentException] if the [value] is blank.
 */
@JvmInline
@SinceKotoolsTypes("1.2")
public value class NotBlankString
@Throws(IllegalArgumentException::class)
public constructor(public val value: String) : Comparable<NotBlankString> {
    /** Returns the first character of this [value]. */
    public val first: Char get() = value[0]

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

    init {
        require(value.isNotBlank()) { "Given value shouldn't be blank." }
    }

    /**
     * Returns the character of this [value] at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): Char =
        value[index.value]

    /**
     * Returns the character of this [value] at the specified [index], or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): Char? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    override fun toString(): String = value

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the [other] object.
     */
    public infix operator fun plus(other: Any?): NotBlankString =
        NotBlankString(value + other)

    public companion object {
        /**
         * Returns the [value] as a not blank string, or returns `null` if the
         * [value] is blank.
         */
        public infix fun orNull(value: String): NotBlankString? = try {
            NotBlankString(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
