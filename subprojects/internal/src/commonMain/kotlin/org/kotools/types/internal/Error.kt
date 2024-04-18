package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Represents an error.
 *
 * @constructor Creates an instance of [Error] with the specified [message] and
 * [reason], or throws an [IllegalArgumentException] if the [message] or the
 * [reason] is blank.
 */
@InternalKotoolsTypesApi
public class Error(
    private val message: String,
    private val reason: String
) {
    init {
        val messageIsNotBlank: Boolean = this.message.isNotBlank()
        require(messageIsNotBlank, Companion::BLANK_MESSAGE)
        val reasonIsNotBlank: Boolean = this.reason.isNotBlank()
        require(reasonIsNotBlank, Companion::BLANK_REASON)
    }

    /**
     * Returns the string representation of this error, joining its [message]
     * and [reason].
     */
    override fun toString(): String = "${this.message} ${this.reason}"

    internal companion object {
        const val BLANK_MESSAGE: String = "Error's message shouldn't be blank."
        const val BLANK_REASON: String = "Error's reason shouldn't be blank."
    }
}
