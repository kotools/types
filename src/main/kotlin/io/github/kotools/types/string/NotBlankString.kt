package io.github.kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Transforms the current [String] to a [NotBlankString], or throws an
 * [IllegalArgumentException] if the current value is blank.
 */
@SinceKotoolsTypes("1.0")
public val String.notBlank: NotBlankString get() = NotBlankString create this

/**
 * Transforms the current [String] to a [NotBlankString], or returns `null` if
 * the current value is blank.
 */
@SinceKotoolsTypes("1.0")
public val String.notBlankOrNull: NotBlankString?
    get() = NotBlankString createOrNull this

/** Type representing strings that can't be blank. */
@JvmInline
@SinceKotoolsTypes("1.0")
public value class NotBlankString private constructor(
    @Suppress("MemberVisibilityCanBePrivate") public val value: String
) {
    override fun toString(): String = value

    public infix operator fun get(index: Int): Char = value[index]

    public infix operator fun plus(other: NotBlankString): NotBlankString =
        (value + other.value).notBlank

    internal companion object {
        private const val ERROR_MESSAGE: String =
            "Given value shouldn't be blank."

        infix fun create(value: String): NotBlankString = createOrNull(value)
            ?: throw IllegalArgumentException(ERROR_MESSAGE)

        infix fun createOrNull(value: String): NotBlankString? = value
            .takeIf(String::isNotBlank)
            ?.let(::NotBlankString)
    }
}
