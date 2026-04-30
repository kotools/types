package org.kotools.types

import kotools.types.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [EmailAddress.Companion] type.
 *
 * @since 4.5.1
 */
@ExperimentalKotoolsTypesApi
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
     * SAMPLE: org.kotools.types.EmailAddressCommonSample.equalsOverride
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
     * SAMPLE: org.kotools.types.EmailAddressJavaSample.equalsOverride
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
     * SAMPLE: org.kotools.types.EmailAddressCommonSample.hashCodeOverride
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
     * SAMPLE: org.kotools.types.EmailAddressJavaSample.hashCodeOverride
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
     * SAMPLE: org.kotools.types.EmailAddressCommonSample.toStringOverride
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
     * SAMPLE: org.kotools.types.EmailAddressJavaSample.toStringOverride
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * Returns an email address with the specified [text], or returns `null`
         * if the [text] doesn't match [EmailAddressRegex.Companion.default].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.EmailAddressCommonSample.ofText
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
         * SAMPLE: org.kotools.types.EmailAddressJavaSample.ofText
         * </details>
         *
         * @since 5.1.0
         */
        @JvmStatic
        public infix fun of(text: String): EmailAddress? {
            val regex: EmailAddressRegex = EmailAddressRegex.default()
            return this.of(text, regex)
        }

        /**
         * Returns an email address with the specified [text], or returns `null`
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
         * SAMPLE: org.kotools.types.EmailAddressCommonSample.ofTextRegex
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
         * SAMPLE: org.kotools.types.EmailAddressJavaSample.ofTextRegex
         * </details>
         *
         * @since 5.1.0
         */
        @JvmStatic
        public fun of(text: String, regex: EmailAddressRegex): EmailAddress? =
            if (regex matches text) EmailAddress(text) else null
    }
}
