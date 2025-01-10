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
     * Returns an error message indicating that the specified [number] shouldn't
     * be other than zero.
     */
    public fun invalidZero(number: Number): String =
        "'$number' shouldn't be other than zero."

    /**
     * Returns an error message indicating that the specified [text] is not a
     * valid representation of zero.
     */
    public fun invalidZero(text: String): String =
        "'$text' is not a valid representation of zero."
}
