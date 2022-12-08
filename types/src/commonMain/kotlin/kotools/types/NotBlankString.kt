package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2")
public value class NotBlankString
private constructor(private val value: String) {
    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.let(::NotBlankString)
            ?.let(Result.Companion::success)
            ?: Result.failure(
                IllegalArgumentException("Given string shouldn't be blank.")
            )
    }

    /** Returns this value as a [String]. */
    override fun toString(): String = value
}

/**
 * Returns this string as a [NotBlankString], or [IllegalArgumentException] if
 * this string is blank.
 */
@SinceKotools(Types, "3.2")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this
