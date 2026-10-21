package org.kotools.types.net

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an email address, such as `john.doe@example.com`, split into its
 * [local part][localPart] and its [domain].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * **Problem:** Email addresses are usually represented as plain strings, so
 * every caller validates them with its own rules
 * ([RFC 5322](https://www.rfc-editor.org/rfc/rfc5322) is too permissive for
 * forms, and most simple regular expressions are too strict for real
 * addresses). Equality is also ambiguous: `John@Example.com` and
 * `john@example.com` designate the same mailbox on almost every provider, but
 * not for strings.
 *
 * **Solution:** The [EmailAddress] type guarantees at construction that its
 * value follows one documented grammar, exposes its two parts without further
 * parsing, and compares addresses structurally on their normalized form.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Grammar</b>
 * </summary>
 *
 * ### Grammar
 *
 * A valid email address is defined as:
 *
 * ```
 * email-address = local-part "@" domain
 * local-part = atext {atext} {"." atext {atext}}
 * domain = label "." label {"." label}
 * label = let-dig [{ldh} let-dig]
 * atext = ALPHA | DIGIT | "!" | "#" | "$" | "%" | "&" | "'" | "*" | "+" | "-"
 *     | "/" | "=" | "?" | "^" | "_" | "`" | "{" | "|" | "}" | "~"
 * let-dig = ALPHA | DIGIT
 * ldh = ALPHA | DIGIT | "-"
 * ```
 *
 * _(grammar written in Wirth-style EBNF, where `[]` denotes optionality and
 * `{}` denotes repetition; `ALPHA` and `DIGIT` are ASCII letters and digits)_
 *
 * The local part is the **dot-atom** form of
 * [RFC 5322 section 3.4.1](https://www.rfc-editor.org/rfc/rfc5322#section-3.4.1),
 * using the full `atext` set of
 * [RFC 5322 section 3.2.3](https://www.rfc-editor.org/rfc/rfc5322#section-3.2.3)
 * (the same character set as the
 * [HTML Standard](https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address)).
 * It can't start or end with a dot, and it can't contain two consecutive dots.
 *
 * The domain is a sequence of **at least two** dot-separated labels made of
 * letters, digits and hyphens, where a label can't start or end with a hyphen
 * ([RFC 5321 section 4.1.2](https://www.rfc-editor.org/rfc/rfc5321#section-4.1.2)).
 * Requiring at least one dot rejects single-label domains such as
 * `user@localhost`, which are valid for SMTP and for the HTML Standard but are
 * not "real" internet addresses.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Limits</b>
 * </summary>
 *
 * ### Limits
 *
 * - The local part has at most **64** characters
 * ([RFC 5321 section 4.5.3.1.1](https://www.rfc-editor.org/rfc/rfc5321#section-4.5.3.1.1)).
 * - Each domain label has at most **63** characters
 * ([RFC 1035 section 2.3.4](https://www.rfc-editor.org/rfc/rfc1035#section-2.3.4)).
 * - The whole address has at most **254** characters: the maximum length of a
 * forward-path is 256 characters including its angle brackets
 * ([RFC 5321 section 4.5.3.1.3](https://www.rfc-editor.org/rfc/rfc5321#section-4.5.3.1.3)).
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Case handling</b>
 * </summary>
 *
 * ### Case handling
 *
 * The [local part][localPart] and the [domain] are converted to lowercase when
 * parsing. Domain names are case-insensitive, and
 * [RFC 5321 section 2.4](https://www.rfc-editor.org/rfc/rfc5321#section-2.4)
 * discourages case-sensitive local parts for interoperability, which matches
 * the behavior of the major providers. Therefore, `John.Doe@Example.COM` and
 * `john.doe@example.com` are equal.
 *
 * The original casing is not preserved: for an input that contains uppercase
 * letters, the result of [toString] differs from the parsed text.
 *
 * ```kotlin
 * val address: EmailAddress = EmailAddress.parse("John.Doe@Example.COM")
 * check(address.localPart == "john.doe")
 * check(address.domain == "example.com")
 * check(address.toString() == "john.doe@example.com")
 * ```
 *
 * No other normalization is applied. Provider-specific rules, such as ignoring
 * dots or `+tag` suffixes in the local part, are not part of this type.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Out of scope</b>
 * </summary>
 *
 * ### Out of scope
 *
 * The following forms are valid in
 * [RFC 5321 section 4.1.2](https://www.rfc-editor.org/rfc/rfc5321#section-4.1.2)
 * or [RFC 5322](https://www.rfc-editor.org/rfc/rfc5322#section-3.4.1), but are
 * rejected by this type:
 *
 * - quoted-string local parts (`"john doe"@example.com`),
 * - comments and folding whitespace,
 * - IP address literals as domains (`user@[192.0.2.1]`),
 * - display names and angle brackets (`John <john@example.com>`),
 * - obsolete syntaxes, groups and address lists,
 * - non-ASCII characters in the local part or in the domain.
 *
 * Internationalized addresses ([RFC 6531](https://www.rfc-editor.org/rfc/rfc6531))
 * are not supported in this version. An internationalized domain name must be
 * provided in its ASCII form (A-label, such as `xn--bcher-kva.example`).
 * Supporting them later will only extend the set of valid values.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Standards</b>
 * </summary>
 *
 * ### Standards
 *
 * See the following standards for more information:
 *
 * - [RFC 5321](https://www.rfc-editor.org/rfc/rfc5321): Simple Mail Transfer
 * Protocol.
 * - [RFC 5322](https://www.rfc-editor.org/rfc/rfc5322): Internet Message
 * Format.
 * - [RFC 6531](https://www.rfc-editor.org/rfc/rfc6531): SMTP Extension for
 * Internationalized Email.
 * - [HTML Standard](https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address):
 * Valid email address.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Key features</b>
 * </summary>
 *
 * ### Key features
 *
 * - **Creations:** Create from a string (see [parse] and [parseOrNull]).
 * - **Comparisons:** Compare email addresses using
 * [structural equality][EmailAddress.equals] (`x == y`, `x != y`).
 * - **Accesses:** Read the [local part][localPart] and the [domain].
 * - **Conversions:** Convert to its string representation (see
 * [EmailAddress.toString]).
 * </details>
 */
@ExperimentalKotoolsTypesApi
public class EmailAddress private constructor(
    /**
     * The part of this email address before the `@` character, in lowercase.
     *
     * It is a dot-atom of at most **64** characters (see the grammar and the
     * limits of the [EmailAddress] type).
     *
     * ```kotlin
     * val address: EmailAddress = EmailAddress.parse("john.doe@example.com")
     * check(address.localPart == "john.doe")
     * ```
     */
    public val localPart: String,
    /**
     * The part of this email address after the `@` character, in lowercase.
     *
     * It contains at least two dot-separated labels (see the grammar and the
     * limits of the [EmailAddress] type).
     *
     * ```kotlin
     * val address: EmailAddress = EmailAddress.parse("john.doe@example.com")
     * check(address.domain == "example.com")
     * ```
     */
    public val domain: String
) {
    // --------------------------- Factory functions ---------------------------

    /** Contains class-level declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * Returns an [EmailAddress] representing the specified [text], or
         * throws an [IllegalArgumentException] if the [text] is not a valid
         * email address.
         *
         * A valid email address follows the grammar and the limits described in
         * the [EmailAddress] type. The [local part][EmailAddress.localPart] and
         * the [domain][EmailAddress.domain] of the result are converted to
         * lowercase, so calling this function with `John@Example.COM` and
         * `john@example.com` produces equal email addresses.
         *
         * ```kotlin
         * val address: EmailAddress = EmailAddress.parse("John@Example.COM")
         * check(address.toString() == "john@example.com")
         * ```
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        @Suppress("UNUSED_PARAMETER")
        public fun parse(text: String): EmailAddress = TODO()

        /**
         * Returns an [EmailAddress] representing the specified [text], or
         * returns `null` if the [text] is not a valid email address.
         *
         * A valid email address follows the grammar and the limits described in
         * the [EmailAddress] type. The [local part][EmailAddress.localPart] and
         * the [domain][EmailAddress.domain] of the result are converted to
         * lowercase, so calling this function with `John@Example.COM` and
         * `john@example.com` produces equal email addresses.
         *
         * ```kotlin
         * val address: EmailAddress? =
         *     EmailAddress.parseOrNull("John@Example.COM")
         * check(address?.toString() == "john@example.com")
         * check(EmailAddress.parseOrNull("john@localhost") == null)
         * ```
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @JvmSynthetic
        @Suppress("RedundantNullableReturnType", "UNUSED_PARAMETER")
        public fun parseOrNull(text: String): EmailAddress? = TODO()
    }

    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [EmailAddress],
     * and has the same [local part][localPart] and [domain] as this email
     * address, or returns `false` otherwise.
     *
     * Because both parts are converted to lowercase when parsing, this
     * comparison is case-insensitive with respect to the parsed texts.
     *
     * ```kotlin
     * val x: EmailAddress = EmailAddress.parse("John@Example.COM")
     * val y: EmailAddress = EmailAddress.parse("john@example.com")
     * check(x == y)
     * ```
     */
    override fun equals(other: Any?): Boolean = TODO()

    /**
     * Returns a hash code value for this email address.
     *
     * Equal email addresses (see [equals]) have the same hash code.
     */
    override fun hashCode(): Int = TODO()

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this email address, which is its
     * [local part][localPart] and its [domain] separated by an `@` character,
     * in lowercase.
     *
     * Parsing the result of this function produces an email address equal to
     * this one. The result equals the text given to [parse] only if that text
     * is already in lowercase.
     *
     * ```kotlin
     * val address: EmailAddress = EmailAddress.parse("john.doe@example.com")
     * check(address.toString() == "john.doe@example.com")
     * ```
     */
    override fun toString(): String = TODO()
}
