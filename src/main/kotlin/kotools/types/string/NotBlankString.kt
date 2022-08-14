package kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.*

// ---------- Comparisons ----------

/**
 * Compares this value lexicographically with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("2.0")
public infix operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.value)

// ---------- Conversions ----------

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
public constructor(public val value: String) : Comparable<String> {
    init {
        require(value.isNotBlank()) { "Given value shouldn't be blank." }
    }

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

    // ---------- Positional access operations ----------

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
     * Returns the character of this [value] at the specified [index], or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): Char? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    // ---------- Binary operations ----------

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the [other] object.
     */
    public infix operator fun plus(other: Any?): NotBlankString =
        NotBlankString(value + other)

    // ---------- Comparisons ----------

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    @SinceKotoolsTypes("2.0")
    override infix fun compareTo(other: String): Int = value.compareTo(other)

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: NotBlankString): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a non-zero int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents `0`.
     */
    @SinceKotoolsTypes("2.1")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /**
     * Returns this [value] as a non-zero int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents `0`.
     */
    @SinceKotoolsTypes("2.1")
    public fun toNonZeroIntOrNull(): NonZeroInt? = value.toNonZeroIntOrNull()

    /**
     * Returns this [value] as a positive int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly negative number.
     */
    @SinceKotoolsTypes("2.1")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toPositiveInt(): PositiveInt = value.toPositiveInt()

    /**
     * Returns this [value] as a positive int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents a strictly
     * negative number.
     */
    @SinceKotoolsTypes("2.1")
    public fun toPositiveIntOrNull(): PositiveInt? =
        value.toPositiveIntOrNull()

    /**
     * Returns this [value] as a strictly positive int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("2.1")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] is not a valid representation of a number or if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("2.1")
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        value.toStrictlyPositiveIntOrNull()

    /**
     * Returns this [value] as a negative int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly positive number.
     */
    @SinceKotoolsTypes("2.1")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    /**
     * Returns this [value] as a negative int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents a strictly
     * positive number.
     */
    @SinceKotoolsTypes("2.1")
    public fun toNegativeIntOrNull(): NegativeInt? =
        value.toNegativeIntOrNull()

    override fun toString(): String = value

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
