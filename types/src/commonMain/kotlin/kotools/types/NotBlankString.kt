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
     * Returns the character of this string at the specified [index], or an
     * [IndexOutOfBoundsException] if the [index] is out of bounds, except in
     * Kotlin/JS where the behavior is unspecified.
     */
    public operator fun get(index: StrictlyPositiveInt): Result<Char> =
        get(index.toPositiveInt())

    /**
     * Returns the character of this string at the specified [index], or an
     * [IndexOutOfBoundsException] if the [index] is out of bounds, except in
     * Kotlin/JS where the behavior is unspecified.
     */
    public operator fun get(index: PositiveInt): Result<Char> =
        runCatching { value[index.toInt()] }

    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * this string is less than the [other] one, or a positive number if this
     * string is greater than the [other] one.
     */
    override fun compareTo(other: NotBlankString): Int = compareTo(other.value)

    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * this string is less than the [other] one, or a positive number if this
     * string is greater than the [other] one.
     */
    public operator fun compareTo(other: String): Int = value.compareTo(other)

    /** Concatenates this string with the [other] character. */
    public operator fun plus(other: Char): NotBlankString =
        plus(other.toString())

    /** Concatenates this string with the [other] one. */
    public operator fun plus(other: NotBlankString): NotBlankString =
        plus(other.value)

    /** Concatenates this string with the [other] one. */
    public operator fun plus(other: String): NotBlankString = (value + other)
        .toNotBlankString()
        .getOrThrow()

    /** Returns this value as a [String]. */
    override fun toString(): String = value
}

/**
 * Compares this string lexicographically with the [other] one for order.
 * Returns zero if this string equals the [other] one, a negative number if
 * this string is less than the [other] one, or a positive number if this
 * string is greater than the [other] one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.toString())

/** Concatenates this character with the [other] string. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Char.plus(other: NotBlankString): NotBlankString =
    plus(other.toString())
        .toNotBlankString()
        .getOrThrow()

/**
 * Returns this string as a [NotBlankString], or an [IllegalArgumentException]
 * if this string is blank.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this
