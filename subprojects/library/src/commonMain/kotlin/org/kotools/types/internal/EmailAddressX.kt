package org.kotools.types.internal

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class InvalidEmailAddress(
    private val value: Any,
    private val pattern: Any = EmailAddress.PATTERN
) {
    override fun equals(other: Any?): Boolean = other is InvalidEmailAddress
            && this.value == other.value
            && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(value, pattern)

    override fun toString(): String =
        "\"$value\" is an invalid email address. " +
                "It should match the following pattern: $pattern"
}

internal class InvalidEmailAddressPattern(private val pattern: Any) {
    override fun equals(other: Any?): Boolean =
        other is InvalidEmailAddressPattern && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(pattern)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    override fun toString(): String =
        "$pattern is an invalid email address pattern. " +
                "It should match the following one: ${EmailAddress.PATTERN}"
}
