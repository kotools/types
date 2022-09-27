package kotools.types.string

import kotools.types.core.Holder
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.Validator
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NotBlankString], or throws an
 * [IllegalArgumentException] if the [value] is blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun NotBlankString(value: String): NotBlankString =
    NotBlankStringImplementation(value)

/**
 * Returns the [value] as a [NotBlankString], or returns `null` if the
 * [value] is blank.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NotBlankStringOrNull(value: String): NotBlankString? = try {
    NotBlankString(value)
} catch (_: IllegalArgumentException) {
    null
}

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
    NotBlankStringOrNull(this)

// ---------- Comparisons ----------

/**
 * Compares this value lexicographically with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("2.0")
public infix operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.value)

/** Parent of classes responsible for holding not blank strings. */
@SinceKotoolsTypes("1.2")
public sealed interface NotBlankString : Holder<String>,
    Comparable<NotBlankString> {
    // ---------- Comparisons ----------

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    @SinceKotoolsTypes("2.0")
    public infix fun compareTo(other: String): Int = value.compareTo(other)

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)
}

@JvmInline
@SinceKotoolsTypes("3.0")
private value class NotBlankStringImplementation(override val value: String) :
    NotBlankString {
    init {
        val validator: Validator<String> = Validator(String::isNotBlank)
        require(validator isValid value)
    }
}
