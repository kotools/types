package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.DeprecatedAsErrorSince
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
         * The underlying value is `^\S+@\S+\.\S+$`.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Pattern symbols</b>
         * </summary>
         *
         * Here's the explanation associated to each symbol used in this
         * pattern:
         * - `^` **Beginning.** Matches the beginning of the string, or the
         * beginning of a line if the multiline flag (**m**) is enabled.
         * - `\S` **Not whitespace.** Matches any character that is not a
         * whitespace character (spaces, tabs, line breaks).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `@` **Character.** Match a "@" character (char code 64).
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (**m**) is enabled.
         * </details>
         */
        @Deprecated(
            "Use the 'EmailAddressRegex.Companion.default()' function instead.",
            ReplaceWith(
                "EmailAddressRegex.default().toString()",
                "org.kotools.types.EmailAddressRegex"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_1)
        public const val PATTERN: String = """^\S+@\S+\.\S+$"""

        // ------------------------- Factory functions -------------------------

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * returns `null` if the [text] doesn't match the
         * [default pattern][EmailAddressRegex.Companion.default].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orNullString]
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
        public fun orNull(text: String): EmailAddress? {
            val regex: EmailAddressRegex = EmailAddressRegex.default()
            return if (regex matches text) EmailAddress(text) else null
        }

        /**
         * Creates an instance of [EmailAddress] from the specified [text].
         * Returns `null` if the [text] doesn't match the specified [pattern],
         * or if the [pattern] doesn't match the
         * [default one][EmailAddressRegex.Companion.default].
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @Deprecated(
            "Use the 'EmailAddress.Companion.orNull(String, " +
                    "EmailAddressRegex)' function instead.",
            ReplaceWith(
                "EmailAddressRegex.orNull(pattern)\n" +
                        "\t?.let { EmailAddress.orNull(text, it) }",
                "org.kotools.types.EmailAddress",
                "org.kotools.types.EmailAddressRegex"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_1)
        @JvmSynthetic
        public fun orNull(text: String, pattern: String): EmailAddress? {
            val patternIsInvalid: Boolean = !EmailAddressRegex.default()
                .matches(pattern)
            if (patternIsInvalid) return null
            val textRegex = Regex(pattern)
            return if (text matches textRegex) EmailAddress(text) else null
        }

        /**
         * Returns an email address from the specified [text], or returns `null`
         * if the [text] doesn't match the specified [regex].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orNullStringEmailAddressRegex]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_1)
        @JvmSynthetic
        public fun orNull(
            text: String,
            regex: EmailAddressRegex
        ): EmailAddress? = if (regex matches text) EmailAddress(text) else null

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * throws an [IllegalArgumentException] if the [text] doesn't match the
         * [default regular expression][EmailAddressRegex.default].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orThrowString]
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
         * SAMPLE: [org.kotools.types.EmailAddressJavaSample.orThrowString]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmStatic
        public fun orThrow(text: String): EmailAddress {
            val emailAddress: EmailAddress? = this.orNull(text)
            return requireNotNull(emailAddress) {
                ExceptionMessage.invalidEmailAddress(text)
            }
        }

        /**
         * Creates an instance of [EmailAddress] from the specified [text].
         * Throws an [IllegalArgumentException] if the [text] doesn't match the
         * specified [pattern], or if the [pattern] doesn't match the
         * [default one][EmailAddressRegex.Companion.default].
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @Deprecated(
            "Use the 'EmailAddress.Companion.orThrow(String, " +
                    "EmailAddressRegex)' function instead.",
            ReplaceWith(
                "this.orThrow(text, EmailAddressRegex.orThrow(pattern))",
                "org.kotools.types.EmailAddress",
                "org.kotools.types.EmailAddressRegex"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_1)
        @JvmStatic
        public fun orThrow(text: String, pattern: String): EmailAddress {
            val patternRegex: EmailAddressRegex = EmailAddressRegex.default()
            require(patternRegex matches pattern) {
                ExceptionMessage.invalidEmailAddressPattern(pattern)
            }
            val textRegex = Regex(pattern)
            require(text matches textRegex) {
                ExceptionMessage.invalidEmailAddress(text)
            }
            return EmailAddress(text)
        }

        /**
         * Returns an email address from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't match the specified
         * [regex].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orThrowStringEmailAddressRegex]
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
         * SAMPLE: [org.kotools.types.EmailAddressJavaSample.orThrowStringEmailAddressRegex]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_1)
        @JvmStatic
        public fun orThrow(
            text: String,
            regex: EmailAddressRegex
        ): EmailAddress {
            val emailAddress: EmailAddress? = this.orNull(text, regex)
            return requireNotNull(emailAddress) {
                ExceptionMessage.invalidEmailAddress(text)
            }
        }
    }
}
