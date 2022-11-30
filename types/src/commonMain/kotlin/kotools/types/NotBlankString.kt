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

    // ---------- Binary operations ----------

    /**
     * Compares this value lexicographically with the [other] value for order.
     * Returns zero if this value equals the [other] value, a negative number if
     * this value is less than the [other] value, or a positive number if this
     * value is greater than the [other] value.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    // ---------- Conversions ----------

    /** Returns this value as a [String]. */
    override fun toString(): String = value
}
