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
        // --------------- Factory functions for email addresses ---------------

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

        // ------------------- Factory functions for numbers -------------------

        /**
         * Returns an exception message indicating that the specified [number]
         * is not zero.
         */
        public fun nonZero(number: Number): ExceptionMessage =
            ExceptionMessage("'$number' is not zero.")

        /**
         * Returns an exception message indicating that the specified [text] is
         * an invalid representation of zero.
         */
        public fun nonZero(text: String): ExceptionMessage =
            ExceptionMessage("'$text' is an invalid representation of zero.")

        /**
         * Returns an exception message indicating that the specified [number]
         * is not greater than zero.
         */
        public fun nonPositive(number: Number): ExceptionMessage =
            ExceptionMessage("'$number' is not greater than zero.")

        // ----------------- Factory functions for exceptions ------------------

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
