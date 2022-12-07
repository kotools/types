package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/**
 * Representation of strings having at least one character, excluding
 * whitespaces.
 */
@JvmInline
@SinceKotools(Types, "3.2")
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

    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * this string is less than the [other] one, or a positive number if this
     * string is greater than the [other] one.
     */
    override fun compareTo(other: NotBlankString): Int = compareTo(other.value)

    /** Returns this value as a [String]. */
    override fun toString(): String = value
}

/** The first character of this string. */
@SinceKotools(Types, "3.2")
public val NotBlankString.first: Char get() = toString()[0]

/** The length of this string. */
@SinceKotools(Types, "3.2")
public val NotBlankString.length: StrictlyPositiveInt
    get() = toString()
        .length
        .toStrictlyPositiveInt()
        .getOrThrow()

/**
 * Returns the character of this string at the specified [index], or an
 * [IndexOutOfBoundsException] if the [index] is out of bounds, except in
 * Kotlin/JS where the behavior is unspecified.
 */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.get(index: PositiveInt): Result<Char> =
    runCatching { toString()[index.toInt()] }

/**
 * Returns the character of this string at the specified [index], or an
 * [IndexOutOfBoundsException] if the [index] is out of bounds, except in
 * Kotlin/JS where the behavior is unspecified.
 */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.get(
    index: StrictlyPositiveInt
): Result<Char> = get(index.toPositiveInt())

/**
 * Compares this string lexicographically with the [other] one for order.
 * Returns zero if this string equals the [other] one, a negative number if
 * this string is less than the [other] one, or a positive number if this
 * string is greater than the [other] one.
 */
@SinceKotools(Types, "3.2")
public operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.toString())

/**
 * Compares this string lexicographically with the [other] one for order.
 * Returns zero if this string equals the [other] one, a negative number if
 * this string is less than the [other] one, or a positive number if this
 * string is greater than the [other] one.
 */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.compareTo(other: String): Int = toString()
    .compareTo(other)

/** Concatenates this string with the [other] one. */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.plus(other: String): NotBlankString {
    val result: String = toString() + other
    return result.toNotBlankString().getOrThrow()
}

/** Concatenates this string with the [other] one. */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.plus(
    other: NotBlankString
): NotBlankString = this + other.toString()

/** Concatenates this string with the [other] character. */
@SinceKotools(Types, "3.2")
public operator fun NotBlankString.plus(other: Char): NotBlankString =
    this + other.toString()

/** Concatenates this character with the [other] string. */
@SinceKotools(Types, "3.2")
public operator fun Char.plus(other: NotBlankString): NotBlankString {
    val result: String = this + other.toString()
    return result.toNotBlankString().getOrThrow()
}

/**
 * Returns this string as a [NotBlankString], or an [IllegalArgumentException]
 * if this string is blank.
 */
@SinceKotools(Types, "3.2")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this
