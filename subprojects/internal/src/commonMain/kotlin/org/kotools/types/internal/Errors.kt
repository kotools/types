package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/** Classes inheriting from this interface represent an error. */
@InternalKotoolsTypesApi
public interface Error {
    /** The message of this error. */
    public val message: String

    /** Formats the string representation of the specified [value]. */
    public fun format(value: Any): String = "\"$value\""
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
        get() {
            val formattedValue: String = super.format(this.value)
            val formattedPattern: String = super.format(this.pattern)
            return "$formattedValue is an invalid email address. It should " +
                    "match the following pattern: $formattedPattern."
        }
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
        get() {
            val formattedPattern: String = super.format(this.pattern)
            val formattedValidationPattern: String =
                super.format(this.validationPattern)
            return "$formattedPattern is invalid for validating email " +
                    "addresses. It should match the following pattern: " +
                    "$formattedValidationPattern."
        }
}
