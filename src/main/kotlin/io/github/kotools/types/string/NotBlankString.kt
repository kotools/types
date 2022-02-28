package io.github.kotools.types.string

/**
 * Transforms the current [String] to a [NotBlankString], or throws an
 * [IllegalArgumentException] if the current value is blank.
 */
public val String.notBlank: NotBlankString get() = NotBlankString create this

/**
 * Transforms the current [String] to a [NotBlankString], or returns `null` if
 * the current value is blank.
 */
public val String.notBlankOrNull: NotBlankString?
    get() = NotBlankString createOrNull this

@JvmInline
public value class NotBlankString private constructor(
    public val value: String
) {
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
