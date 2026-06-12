package org.kotools.types.internal

/**
 * Returns an error message with specified [reason] and [value].
 *
 * The resulting message follows the `[reason]: [value]` structure, with the
 * first letter capitalized, and no trailing point.
 *
 * If the specified [value] is a [String], it is wrapped in single quotes as a
 * visual delimiter (e.g., `Invalid integer representation: 'abc'`), with any
 * single quote it contains escaped with a backslash (e.g., `Invalid email
 * address: 'o\'brien@example.com'`). Otherwise, the [value] is left unquoted
 * (e.g., `Non-positive integer: -123`).
 *
 * If the [value] is not specified, this function only returns the capitalized
 * [reason] (e.g., `Division by zero`).
 *
 * In case of blank [reason], this functions throws an
 * [IllegalArgumentException].
 */
@InternalKotoolsTypesApi
public fun errorMessage(reason: String, value: Any? = null): String {
    require(reason.isNotBlank()) { "Blank reason" }
    val formattedReason: String = reason.replaceFirstChar {
        if (it.isUpperCase()) it else it.titlecaseChar()
    }
    return when (value) {
        null -> formattedReason
        is String -> {
            val escapedValue: String = value.replace("'", "\\'")
            "$formattedReason: '$escapedValue'"
        }
        else -> "$formattedReason: $value"
    }
}
