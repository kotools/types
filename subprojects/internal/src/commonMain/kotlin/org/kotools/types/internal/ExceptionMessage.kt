package org.kotools.types.internal

import kotools.types.internal.hashCodeOf

/** Represents an exception message. */
@InternalKotoolsTypesApi
public class ExceptionMessage private constructor(private val text: String) {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [ExceptionMessage]
     * with the same string representation as this one, or returns `false`
     * otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is ExceptionMessage && this.text == other.text

    /** Returns a hash code value for this exception message. */
    override fun hashCode(): Int = hashCodeOf(this.text)

    // ------------------------------ Conversions ------------------------------

    /** Returns the string representation of this exception message. */
    override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [ExceptionMessage] type. */
    public companion object {
        // -------------- Email address related factory functions --------------

        /**
         * Returns an exception message indicating that the specified [text] is
         * an invalid email address.
         */
        public fun invalidEmailAddress(text: String): ExceptionMessage =
            ExceptionMessage("'$text' is an invalid email address.")

        /**
         * Returns an exception message indicating that the specified [pattern]
         * is invalid for validating email addresses.
         */
        public fun invalidEmailAddressPattern(
            pattern: String
        ): ExceptionMessage = ExceptionMessage(
            "'$pattern' is invalid for validating email addresses."
        )

        // ----------------- Number related factory functions ------------------

        /**
         * Returns an exception message indicating that the specified [number]
         * is not positive.
         */
        public fun nonPositive(number: Number): ExceptionMessage =
            ExceptionMessage("'$number' is not positive (> 0).")

        // ---------------------- Other factory functions ----------------------

        /**
         * Creates an instance of [ExceptionMessage] from the specified [text],
         * or throws an [IllegalArgumentException] if the [text] is blank.
         */
        public fun orThrow(text: String): ExceptionMessage {
            require(text.isNotBlank()) {
                "Exception's message shouldn't be blank."
            }
            return ExceptionMessage(text)
        }

        /**
         * Returns an exception message from the specified [throwable], or
         * throws an [IllegalArgumentException] if the [throwable]'s message is
         * `null` or [blank][CharSequence.isBlank].
         */
        public fun from(throwable: Throwable): ExceptionMessage {
            val text: String = requireNotNull(throwable.message) {
                "Exception's message is null ($throwable)."
            }
            require(text.isNotBlank()) {
                "Exception's message is blank ($throwable)."
            }
            return ExceptionMessage(text)
        }
    }
}
