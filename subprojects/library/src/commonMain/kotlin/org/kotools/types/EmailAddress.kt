package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.InvalidEmailAddress
import org.kotools.types.internal.InvalidEmailAddressPattern
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * You can use the [EmailAddress.Companion.fromString] or the
 * [EmailAddress.Companion.fromStringOrNull] functions for creating an instance
 * of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public class EmailAddress private constructor(private val value: String) {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [EmailAddress] and
     * has the same string representation as this email address, or returns
     * `false` otherwise.
     *
     * <br>
     * <details open>
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
        other is EmailAddress && this.value == other.value

    /**
     * Returns a hash code value for this email address.
     *
     * <br>
     * <details open>
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
    final override fun hashCode(): Int = hashCodeOf(this.value)

    // ------------------------------ Converters -------------------------------

    /**
     * Returns the string representation of this email address.
     *
     * <br>
     * <details open>
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
    final override fun toString(): String = value

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
         * <details open>
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
        public const val PATTERN: String = "^\\S+@\\S+\\.\\S+\$"

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or throws an [IllegalArgumentException] if
         * the string representation of [value] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
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
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringAny]
         * </details>
         * <br>
         *
         * You can use the [fromStringOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun fromString(value: Any): EmailAddress =
            fromString(value, PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value].
         * Throws an [IllegalArgumentException] if the string representation of
         * [value] doesn't match the string representation of the specified
         * [pattern], or if the string representation of [pattern] doesn't match
         * the [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
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
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringAnyAny]
         * </details>
         * <br>
         *
         * You can use the [fromStringOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value] or
         * [pattern].
         */
        @JvmStatic
        public fun fromString(value: Any, pattern: Any): EmailAddress {
            val patternAsString: String = pattern.toString()
            val validationPattern: String = PATTERN
            val regex = Regex(validationPattern)
            require(patternAsString matches regex) {
                InvalidEmailAddressPattern(patternAsString, validationPattern)
            }
            return fromString(value, patternAsString)
        }

        private fun fromString(value: Any, pattern: String): EmailAddress {
            val address: EmailAddress? = fromStringOrNull(value, pattern)
            return requireNotNull(address) {
                InvalidEmailAddress("$value", pattern)
            }
        }

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or returns `null` if the string
         * representation of [value] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.fromStringOrNullAny]
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
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringOrNullAny]
         * </details>
         * <br>
         *
         * You can use the [fromString] function for throwing an exception
         * instead of returning `null` in case of invalid [value].
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any): EmailAddress? =
            fromStringOrNull(value, PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value].
         * Returns `null` if the string representation of [value] doesn't match
         * the string representation of the specified [pattern], or if the
         * string representation of [pattern] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.fromStringOrNullAnyAny]
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
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.fromStringOrNullAnyAny]
         * </details>
         * <br>
         *
         * You can use the [fromString] function for throwing an exception
         * instead of returning `null` in case of invalid [value] or [pattern].
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any, pattern: Any): EmailAddress? {
            val patternAsString: String = pattern.toString()
            val defaultRegex = Regex(PATTERN)
            if (!patternAsString.matches(defaultRegex)) return null
            return fromStringOrNull(value, patternAsString)
        }

        private fun fromStringOrNull(
            value: Any,
            pattern: String
        ): EmailAddress? {
            val valueAsString: String = value.toString()
            val regex = Regex(pattern)
            return if (valueAsString matches regex) EmailAddress(valueAsString)
            else null
        }

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or returns `null` if the string
         * representation of [value] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressCompanionCommonSample.orNullAny]
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
         * SAMPLE: [org.kotools.types.EmailAddressCompanionJavaSample.orNullAny]
         * </details>
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun orNull(value: Any): EmailAddress? {
            val valueAsString = "$value"
            val regex = Regex(this.PATTERN)
            val valueMatchesRegex: Boolean = valueAsString.matches(regex)
            return if (valueMatchesRegex) EmailAddress(valueAsString) else null
        }
    }
}
