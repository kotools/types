package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning

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
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.equalsOverride]
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is EmailAddress && this.text == other.text

    /**
     * Returns a hash code value for this email address.
     *
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.hashCodeOverride]
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf(this.text)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this email address.
     *
     * SAMPLE: [org.kotools.types.EmailAddressCommonSample.toStringOverride]
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        // ------------------------- Factory functions -------------------------

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * returns `null` if the [text] doesn't match the
         * [default regular expression][EmailAddressRegex.Companion.default].
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orNullString]
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        public fun orNull(text: String): EmailAddress? {
            val regex: EmailAddressRegex = EmailAddressRegex.default()
            return this.orNull(text, regex)
        }

        /**
         * Returns an email address from the specified [text], or returns `null`
         * if the [text] doesn't match the specified [regex].
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orNullStringEmailAddressRegex]
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_1)
        public fun orNull(
            text: String,
            regex: EmailAddressRegex
        ): EmailAddress? = if (regex matches text) EmailAddress(text) else null

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * throws an [IllegalArgumentException] if the [text] doesn't match the
         * [default regular expression][EmailAddressRegex.default].
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orThrowString]
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        public fun orThrow(text: String): EmailAddress {
            val emailAddress: EmailAddress? = this.orNull(text)
            return requireNotNull(emailAddress) {
                ExceptionMessage.invalidEmailAddress(text)
            }
        }

        /**
         * Returns an email address from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't match the specified
         * [regex].
         *
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.orThrowStringEmailAddressRegex]
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_1)
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
