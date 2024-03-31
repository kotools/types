package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@OptIn(InternalKotoolsTypesApi::class)
public class EmailAddress private constructor() {
    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * The pattern that an email address should match.
         *
         * The underlying pattern is `^\S+@\S+\.\S+$`.
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
    }
}
