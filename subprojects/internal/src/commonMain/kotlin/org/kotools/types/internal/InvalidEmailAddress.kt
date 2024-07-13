package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Represents an error indicating that the specified [text] is an invalid email
 * address.
 *
 * @constructor Creates an instance of [InvalidEmailAddress], indicating that
 * the specified [text] is an invalid email address and should match the
 * specified [pattern]. Throws an [IllegalArgumentException] if the [text] or
 * the [pattern] is [blank][String.isBlank].
 */
@InternalKotoolsTypesApi
public class InvalidEmailAddress(
    private val text: String,
    private val pattern: String
) {
    init {
        val valueIsNotBlank: Boolean = this.text.isNotBlank()
        require(valueIsNotBlank, Companion::BLANK_VALUE)
        val patternIsNotBlank: Boolean = this.pattern.isNotBlank()
        require(patternIsNotBlank, Companion::BLANK_PATTERN)
    }

    /** Returns the string representation of this error. */
    override fun toString(): String {
        val message = "'${this.text}' is an invalid email address."
        val reason = "It should match the following pattern: '${this.pattern}'."
        val error = Error(message, reason)
        return error.toString()
    }

    internal companion object {
        const val BLANK_VALUE: String =
            "Value for creating an email address shouldn't be blank."
        const val BLANK_PATTERN: String =
            "Pattern for validating email addresses shouldn't be blank."
    }
}
