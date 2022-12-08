package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotlin.jvm.JvmInline

/**
 * Representation of strings having at least one character, excluding
 * whitespaces.
 */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NotBlankString
private constructor(private val value: String) : Comparable<NotBlankString> {
    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.let(::NotBlankString)
            ?.let(Result.Companion::success)
            ?: Result.failure(
                IllegalArgumentException("Given string shouldn't be blank.")
            )
    }

    /** The first character of this string. */
    public val first: Char get() = value[0]

    /** The length of this string. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * this string is less than the [other] one, or a positive number if this
     * string is greater than the [other] one.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    /** Returns this value as a [String]. */
    override fun toString(): String = value
}

/**
 * Returns this string as a [NotBlankString], or an [IllegalArgumentException]
 * if this string is blank.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this
