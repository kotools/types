package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Represents an error indicating that we are unable to create an instance of
 * the type corresponding to the specified [typeQualifiedName] from the
 * [invalidValue], for the specified [reason].
 */
@InternalKotoolsTypesApi
public class InstanceCreationError(
    private val typeQualifiedName: String,
    private val invalidValue: Any,
    private val reason: String
) {
    init {
        val typeQualifiedNameIsNotBlank: Boolean =
            this.typeQualifiedName.isNotBlank()
        require(typeQualifiedNameIsNotBlank, Companion::BLANK_QUALIFIED_NAME)
        val reasonIsNotBlank: Boolean = this.reason.isNotBlank()
        require(reasonIsNotBlank, Companion::BLANK_REASON)
    }

    /** Returns the string representation of this error. */
    override fun toString(): String {
        val message = "Unable to create an instance of " +
                "'${this.typeQualifiedName}' from '${this.invalidValue}'."
        val error = Error(message, this.reason)
        return error.toString()
    }

    internal companion object {
        const val BLANK_QUALIFIED_NAME: String = "Instance creation's error " +
                "shouldn't have a blank type's qualified name."
        const val BLANK_REASON: String =
            "Instance creation's error shouldn't have a blank reason."
    }
}
