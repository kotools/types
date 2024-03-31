package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmStatic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * You can use the [EmailAddress.Companion.fromString] or the
 * [EmailAddress.Companion.fromStringOrNull] functions for creating an instance
 * of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class EmailAddress private constructor() {
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
         * beginning of a line if the multiline flag (m) is enabled.
         * - `\S` **Not whitespace.** Matches any character that is not a
         * whitespace character (spaces, tabs, line breaks).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `@` **Character.** Match a "@" character (char code 64).
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (m) is enabled.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.patternSample.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.patternSample.md
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
         * SAMPLE: EmailAddressCompanionKotlinSample.fromStringSample.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromStringSample.md
         * </details>
         * <br>
         *
         * You can use the [fromStringOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun fromString(value: Any): EmailAddress {
            val address: EmailAddress? = fromStringOrNull(value)
            return requireNotNull(address) {
                InvalidEmailAddress(value).message
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
         * SAMPLE: EmailAddressCompanionKotlinSample.fromStringOrNull_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromStringOrNull_Any.md
         * </details>
         * <br>
         *
         * You can use the [fromString] function for throwing an exception
         * instead of returning `null` in case of invalid [value].
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any): EmailAddress? {
            val valueAsString: String = value.toString()
            val regex = Regex(PATTERN)
            return if (valueAsString matches regex) EmailAddress() else null
        }

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or returns `null` if the [value] doesn't
         * match the specified [pattern], or if the [pattern] doesn't match the
         * [default one][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.fromStringOrNull_Any_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromStringOrNull_Any_Any.md
         * </details>
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any, pattern: Any): EmailAddress? {
            val patternAsString: String = pattern.toString()
            val defaultRegex = Regex(PATTERN)
            if (!patternAsString.matches(defaultRegex)) return null
            val valueAsString: String = value.toString()
            val customRegex = Regex(patternAsString)
            return if (valueAsString matches customRegex) EmailAddress()
            else null
        }
    }
}

internal class InvalidEmailAddress(value: Any) : IllegalArgumentException() {
    override val message: String = "\"$value\" is an invalid email address."
}
