package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/** Classes inheriting from this interface represent an error. */
@InternalKotoolsTypesApi
public interface Error {
    /** The message of this error. */
    public val message: String
}

// ------------------------------- EmailAddress --------------------------------

/**
 * Error indicating that the specified [value] is an invalid email address and
 * should match the specified [pattern].
 */
@InternalKotoolsTypesApi
public class InvalidEmailAddressError(
    private val value: Any,
    private val pattern: Any
) : Error {
    override val message: String
        get() = "\"$value\" is an invalid email address. " +
                "It should match the following pattern: \"$pattern\"."
}

/**
 * Error indicating that the specified [pattern] is invalid for validating email
 * addresses, and should match the [validationPattern].
 */
@InternalKotoolsTypesApi
public class InvalidEmailAddressPatternError(
    private val pattern: Any,
    private val validationPattern: Any
) : Error {
    override val message: String
        get() = "\"$pattern\" is invalid for validating email addresses. " +
                "It should match the following pattern: \"$validationPattern\"."
}
