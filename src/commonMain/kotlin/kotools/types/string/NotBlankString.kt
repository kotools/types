package kotools.types.string

import kotools.types.core.Holder
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a [NotBlankString], or throws an
 * [IllegalArgumentException] if this value is blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/**
 * Returns this value as a [NotBlankString], or returns `null` if this value is
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
public constructor(override val value: String) : Holder<String> {
    init {
        require(value.isNotBlank()) {
            val type: String = this::class.simpleName!!
            "$type doesn't accept blank values."
        }
    }

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
