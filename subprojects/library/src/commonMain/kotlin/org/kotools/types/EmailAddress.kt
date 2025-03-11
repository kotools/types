package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [EmailAddress.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public class EmailAddress private constructor(private val text: String) {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [EmailAddress] and
     * has the same string representation as this email address, or returns
     * `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.equalsOverride]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressJavaSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is EmailAddress && this.text == other.text

    /**
     * Returns a hash code value for this email address.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.hashCodeOverride]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressJavaSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf(this.text)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this email address.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.toStringOverride]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * The pattern that an email address should match.
         *
         * The underlying value is [`^\S+@\S+\.\S+$`](https://regexr.com/8arr2).
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this property from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.patternSample]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this property from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.patternSample]
         * </details>
         */
        public const val PATTERN: String = """^\S+@\S+\.\S+$"""

        // ------------------------- Factory functions -------------------------

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * returns `null` if the [text] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orNullString]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmSynthetic
        public fun orNull(text: String): EmailAddress? = try {
            this.orThrow(text)
        } catch (exception: IllegalArgumentException) {
            null
        }

        /**
         * Creates an instance of [EmailAddress] from the specified [text].
         * Returns `null` if the [text] doesn't match the specified [pattern],
         * or if the [pattern] doesn't match the [default one][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orNullStringString]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmSynthetic
        public fun orNull(text: String, pattern: String): EmailAddress? = try {
            this.orThrow(text, pattern)
        } catch (exception: IllegalArgumentException) {
            null
        }

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * throws an [IllegalArgumentException] if the [text] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orThrowString]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.orThrowString]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmStatic
        public fun orThrow(text: String): EmailAddress {
            val pattern: String = this.PATTERN
            val regex = Regex(pattern)
            require(text matches regex) { this.invalidText(text, pattern) }
            return EmailAddress(text)
        }

        /**
         * Creates an instance of [EmailAddress] from the specified [text].
         * Throws an [IllegalArgumentException] if the [text] doesn't match the
         * specified [pattern], or if the [pattern] doesn't match the
         * [default one][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orThrowStringString]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.orThrowStringString]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmStatic
        public fun orThrow(text: String, pattern: String): EmailAddress {
            val patternRegex = Regex(this.PATTERN)
            require(pattern matches patternRegex) {
                val expected: String = patternRegex.pattern
                this.invalidPattern(pattern, expected)
            }
            val textRegex = Regex(pattern)
            require(text matches textRegex) { this.invalidText(text, pattern) }
            return EmailAddress(text)
        }

        // ------------------------ Exception messages -------------------------

        @JvmSynthetic
        internal fun invalidPattern(
            pattern: String,
            expected: String
        ): ExceptionMessage {
            val message =
                "'$pattern' is invalid for validating email addresses."
            val reason = "It should match the following pattern: '$expected'."
            return ExceptionMessage.orThrow("$message $reason")
        }

        @JvmSynthetic
        internal fun invalidText(
            text: String,
            pattern: String
        ): ExceptionMessage {
            val message = "'$text' is an invalid email address."
            val reason = "It should match the following pattern: '$pattern'."
            return ExceptionMessage.orThrow("$message $reason")
        }
    }
}
