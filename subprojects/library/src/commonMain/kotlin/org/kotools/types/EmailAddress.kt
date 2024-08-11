package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.DeprecatedAsErrorSince
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.InvalidEmailAddress
import org.kotools.types.internal.InvalidEmailAddressPattern
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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [text], or throws an [IllegalArgumentException] if
         * the string representation of [text] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.fromStringAny]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringAny]
         * </details>
         * <br>
         *
         * See the [fromStringOrNull] method for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        public fun fromString(text: Any): EmailAddress =
            fromString(text, PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [text].
         * Throws an [IllegalArgumentException] if the string representation of
         * [text] doesn't match the string representation of the specified
         * [pattern], or if the string representation of [pattern] doesn't match
         * the [default pattern][PATTERN].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.fromStringAnyAny]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringAnyAny]
         * </details>
         * <br>
         *
         * See the [fromStringOrNull] method for returning `null` instead of
         * throwing an exception in case of invalid [text] or [pattern].
         */
        @JvmStatic
        public fun fromString(text: Any, pattern: Any): EmailAddress {
            val patternAsString: String = pattern.toString()
            val validationPattern: String = this.PATTERN
            val regex = Regex(validationPattern)
            require(patternAsString matches regex) {
                InvalidEmailAddressPattern(patternAsString, validationPattern)
            }
            return this.fromString(text, patternAsString)
        }

        private fun fromString(text: Any, pattern: String): EmailAddress {
            val address: EmailAddress? = fromStringOrNull(text, pattern)
            return requireNotNull(address) {
                InvalidEmailAddress("$text", pattern)
            }
        }

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [text], or returns `null` if the string
         * representation of [text] doesn't match the
         * [default pattern][PATTERN].
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [fromString] method for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @Deprecated(
            "Use the 'orNull' method instead.",
            ReplaceWith(
                "EmailAddress.orNull(\"\$text\")",
                "org.kotools.types.EmailAddress"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun fromStringOrNull(text: Any): EmailAddress? =
            this.fromStringOrNull(text, this.PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [text].
         * Returns `null` if the string representation of [text] doesn't match
         * the string representation of the specified [pattern], or if the
         * string representation of [pattern] doesn't match the
         * [default pattern][PATTERN].
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [fromString] method for throwing an exception instead of
         * returning `null` in case of invalid [text] or [pattern].
         */
        @Deprecated(
            "Use the 'orNull' method instead.",
            ReplaceWith(
                "EmailAddress.orNull(\"\$text\", \"\$pattern\")",
                "org.kotools.types.EmailAddress"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun fromStringOrNull(text: Any, pattern: Any): EmailAddress? {
            val patternAsString: String = pattern.toString()
            val defaultRegex = Regex(PATTERN)
            if (!patternAsString.matches(defaultRegex)) return null
            return fromStringOrNull(text, patternAsString)
        }

        private fun fromStringOrNull(
            text: Any,
            pattern: String
        ): EmailAddress? {
            val valueAsString: String = text.toString()
            val regex = Regex(pattern)
            return if (valueAsString matches regex) EmailAddress(valueAsString)
            else null
        }

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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orNullString]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orNullStringString]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
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
         * Here's an example of calling this method from Kotlin code:
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
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.orThrowString]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun orThrow(text: String): EmailAddress {
            val pattern: String = this.PATTERN
            val regex = Regex(pattern)
            require(text matches regex) { InvalidEmailAddress(text, pattern) }
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
         * Here's an example of calling this method from Kotlin code:
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
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.orThrowStringString]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [text] or [pattern].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun orThrow(text: String, pattern: String): EmailAddress {
            val validationPattern: String = this.PATTERN
            val patternRegex = Regex(validationPattern)
            require(pattern matches patternRegex) {
                InvalidEmailAddressPattern(pattern, validationPattern)
            }
            val textRegex = Regex(pattern)
            require(text matches textRegex) {
                InvalidEmailAddress(text, pattern)
            }
            return EmailAddress(text)
        }
    }
}
