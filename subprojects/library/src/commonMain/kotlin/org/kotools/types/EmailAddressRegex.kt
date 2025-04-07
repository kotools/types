package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents a
 * [regular expression](https://en.wikipedia.org/wiki/Regular_expression) for
 * validating [email addresses][EmailAddress].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [EmailAddressRegex.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
public class EmailAddressRegex private constructor(
    private val pattern: String
) {
    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this regular expression.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.pattern

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddressRegex] type. */
    public companion object {
        /**
         * Returns the default regular expression for validating
         * [email addresses][EmailAddress], corresponding to the following
         * pattern: `^\S+@\S+\.\S+$`.
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
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressRegexCompanionCommonSample.default]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * The Java method generated from this function is named
         * `defaultPattern`, due to the `default` keyword present in this
         * programming language.
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressRegexCompanionJavaSample.defaultPattern]
         * </details>
         */
        @JvmName("defaultPattern")
        @JvmStatic
        public fun default(): EmailAddressRegex =
            EmailAddressRegex("""^\S+@\S+\.\S+$""")

        /**
         * Returns a regular expression for validating
         * [email addresses][EmailAddress] from the specified [pattern], or
         * returns `null` if the [pattern] doesn't match the
         * [default one][EmailAddressRegex.Companion.default].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressRegexCompanionCommonSample.orNull]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception in case of
         * invalid [pattern] instead of returning `null`.
         */
        @JvmSynthetic
        public fun orNull(pattern: String): EmailAddressRegex? {
            val patternIsValid: Boolean = this.default()
                .toString()
                .toRegex()
                .matches(pattern)
            return if (patternIsValid) EmailAddressRegex(pattern) else null
        }

        /**
         * Returns a regular expression for validating
         * [email addresses][EmailAddress] from the specified [pattern], or
         * throws an [IllegalArgumentException] if the [pattern] doesn't match
         * the [default one][EmailAddressRegex.Companion.default].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.EmailAddressRegexCompanionCommonSample.orThrow]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCompanionJavaSample.orThrow]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` in case of invalid
         * [pattern] instead of throwing an exception.
         */
        @JvmStatic
        public fun orThrow(pattern: String): EmailAddressRegex {
            val regex: EmailAddressRegex? = this.orNull(pattern)
            return requireNotNull(regex) {
                ExceptionMessage.invalidEmailAddressPattern(pattern)
            }
        }
    }
}
