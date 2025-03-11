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
        /**
         * Creates an instance of [ExceptionMessage] from the specified [text],
         * or returns `null` if the [text] is blank.
         */
        public fun orNull(text: String): ExceptionMessage? = try {
            this.orThrow(text)
        } catch (_: IllegalArgumentException) {
            null
        }

        /**
         * Creates an instance of [ExceptionMessage] from the message of the
         * specified [throwable], or returns `null` if the [throwable]'s message
         * is blank.
         */
        public fun orNull(throwable: Throwable): ExceptionMessage? = try {
            this.orThrow(throwable)
        } catch (_: IllegalArgumentException) {
            null
        }

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
         * Creates an instance of [ExceptionMessage] from the message of the
         * specified [throwable], or throws an [IllegalArgumentException] if the
         * [throwable]'s message is blank.
         */
        public fun orThrow(throwable: Throwable): ExceptionMessage {
            val text: String = requireNotNull(throwable.message) {
                "Exception's message shouldn't be null."
            }
            return this.orThrow(text)
        }
    }
}
