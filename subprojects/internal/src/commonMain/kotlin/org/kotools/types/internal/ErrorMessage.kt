package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/** Contains a collection of error messages. */
@InternalKotoolsTypesApi
public object ErrorMessage {
    /**
     * Returns an error message indicating that the specified [text] is an
     * invalid email address and should match the specified [pattern].
     */
    public fun invalidEmailAddress(text: String, pattern: String): String {
        val message = "'$text' is an invalid email address."
        val reason = "It should match the following pattern: '$pattern'."
        return "$message $reason"
    }

    /**
     * Returns an error message indicating that the specified [pattern] is
     * invalid for validating email addresses and should match the [expected]
     * one.
     */
    public fun invalidEmailAddressPattern(
        pattern: String,
        expected: String
    ): String {
        val message = "'$pattern' is invalid for validating email addresses."
        val reason = "It should match the following pattern: '$expected'."
        return "$message $reason"
    }

    /**
     * Returns an error message indicating that the string representation of the
     * specified [value] is an invalid representation of zero.
     */
    public fun invalidZero(value: Any): String =
        "'$value' is an invalid representation of zero."
}
