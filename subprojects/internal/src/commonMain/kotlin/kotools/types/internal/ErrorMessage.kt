/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/**
 * Creates an error message from the message of the specified [throwable], or
 * throws an [IllegalArgumentException] if the [throwable] doesn't have a
 * message or have a blank one.
 */
public fun ErrorMessage(throwable: Throwable): ErrorMessage {
    val text: String =
        requireNotNull(throwable.message) { "Throwable should have a message." }
    return ErrorMessage(text)
}

/**
 * Represents an error message.
 *
 * @constructor Creates an error message with the specified [text], or throws
 * an [IllegalArgumentException] if the [text] is [blank][CharSequence.isBlank].
 */
public class ErrorMessage(private val text: String) {
    init {
        val isValid: Boolean = text.isNotBlank()
        require(isValid) { "Error message shouldn't be blank" }
    }

    /**
     * Returns `true` if the [other] object is an [ErrorMessage] having the same
     * value as this error message.
     */
    override fun equals(other: Any?): Boolean =
        other is ErrorMessage && text == other.text

    /** Returns a hash code value for this error message. */
    override fun hashCode(): Int = hashCodeOf(text)

    /** Returns this error message as [String]. */
    override fun toString(): String = text

    /** Contains static declarations for the [ErrorMessage] type. */
    public companion object {
        /**
         * An error message indicating that the specified string shouldn't be
         * blank.
         */
        public val blankString: ErrorMessage by lazy {
            ErrorMessage("Given string shouldn't be blank.")
        }

        /**
         * An error message indicating that the specified collection shouldn't
         * be empty.
         */
        public val emptyCollection: ErrorMessage by lazy {
            ErrorMessage("Given collection shouldn't be empty.")
        }

        /**
         * An error message indicating that the specified map shouldn't be
         * empty.
         */
        public val emptyMap: ErrorMessage by lazy {
            ErrorMessage("Given map shouldn't be empty.")
        }

        /**
         * An error message indicating that the specified number should be other
         * than zero.
         */
        public val zeroNumber: ErrorMessage by lazy {
            ErrorMessage("Number should be other than zero")
        }
    }
}

/**
 * Returns an error message indicating that this number should be greater than
 * zero.
 */
public fun Number.shouldBeGreaterThanZero(): ErrorMessage =
    ErrorMessage("Number should be greater than zero (tried with $this)")

/** Returns an error message indicating that this number should be negative. */
public fun Number.shouldBeNegative(): ErrorMessage =
    ErrorMessage("Number should be negative (tried with $this).")

/** Returns an error message indicating that this number should be positive. */
public fun Number.shouldBePositive(): ErrorMessage =
    ErrorMessage("Number should be positive (tried with $this).")

/**
 * Returns an error message indicating that this number should be strictly
 * negative.
 */
public fun Number.shouldBeStrictlyNegative(): ErrorMessage =
    ErrorMessage("Number should be strictly negative (tried with $this).")

/**
 * Returns an error message indicating that this number should be strictly
 * positive.
 */
public fun Number.shouldBeStrictlyPositive(): ErrorMessage =
    ErrorMessage("Number should be strictly positive (tried with $this).")
