package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Represents an error indicating that the specified [pattern] is invalid for
 * validating email addresses.
 *
 * @constructor Creates an instance of [InvalidEmailAddressPattern], indicating
 * that the specified [pattern] is invalid for validating email addresses and
 * should match the [validationPattern]. Throws an [IllegalArgumentException] if
 * the [pattern] or the [validationPattern] is [blank][String.isBlank].
 */
@InternalKotoolsTypesApi
public class InvalidEmailAddressPattern(
    private val pattern: String,
    private val validationPattern: String
) {
    init {
        val patternIsNotBlank: Boolean = this.pattern.isNotBlank()
        require(patternIsNotBlank) { InvalidEmailAddress.BLANK_PATTERN }
        val validationPatternIsNotBlank: Boolean =
            this.validationPattern.isNotBlank()
        require(validationPatternIsNotBlank) { BLANK_VALIDATION_PATTERN }
    }

    /** Returns the string representation of this error. */
    override fun toString(): String {
        val message = "'$pattern' is invalid for validating email addresses."
        val reason =
            "It should match the following pattern: '$validationPattern'."
        val error = Error(message, reason)
        return error.toString()
    }

    internal companion object {
        const val BLANK_VALIDATION_PATTERN: String =
            "Validation pattern for email address's pattern shouldn't be blank."
    }
}
