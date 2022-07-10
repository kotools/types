package kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt

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
    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

    init {
        require(value.isNotBlank()) { "Given value shouldn't be blank." }
    }

    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

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
