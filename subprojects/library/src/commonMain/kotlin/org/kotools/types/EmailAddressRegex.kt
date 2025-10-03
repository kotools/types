package org.kotools.types

import kotools.types.internal.hashCodeOf
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
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [EmailAddressRegex] with the same
     * [string representation][EmailAddressRegex.toString], or returns `false`
     * otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.equalsOverride]
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
     * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is EmailAddressRegex && this.pattern == other.pattern

    /**
     * Returns a hash code value for this regular expression.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.hashCodeOverride]
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
     * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf(this.pattern)

    // ---------------------- Pattern matching operations ----------------------

    /**
     * Returns `true` if the specified [text] matches this regular expression,
     * or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.matches]
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
     * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.matches]
     * </details>
     */
    public infix fun matches(text: CharSequence): Boolean =
        Regex(this.pattern) matches text

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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.default]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.defaultPattern]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.orNull]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.orThrow]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.orThrow]
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

        /**
         * Returns a regular expression for validating
         * [email addresses][EmailAddress] that only accepts lowercase letters,
         * corresponding to the following pattern: `^[a-z]+@[a-z]+\.[a-z]+$`.
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
         * - `[]` **Character set.** Match any character in the set.
         * - `a-z` **Range.** Matches a character in the range "a" to "z" (char
         * code 97 to 122). Case-sensitive.
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.alphabetic]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.alphabetic]
         * </details>
         */
        @JvmStatic
        public fun alphabetic(): EmailAddressRegex =
            this.orThrow("""^[a-z]+@[a-z]+\.[a-z]+$""")

        /**
         * Returns a regular expression for validating
         * [email addresses][EmailAddress] that only accepts lowercase letters
         * and digits, corresponding to the following pattern:
         * `^[0-9a-z]+@[0-9a-z]+\.[0-9a-z]+$`.
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
         * - `[]` **Character set.** Match any character in the set.
         * - `0-9` **Range.** Matches a character in the range "0" to "9" (char
         * code 48 to 57). Case-sensitive.
         * - `a-z` **Range.** Matches a character in the range "a" to "z" (char
         * code 97 to 122). Case-sensitive.
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexCommonSample.alphanumeric]
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
         * SAMPLE: [org.kotools.types.EmailAddressRegexJavaSample.alphanumeric]
         * </details>
         */
        @JvmStatic
        public fun alphanumeric(): EmailAddressRegex =
            this.orThrow("""^[0-9a-z]+@[0-9a-z]+\.[0-9a-z]+$""")
    }
}
