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
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.ofText]
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
         * SAMPLE: [org.kotools.types.EmailAddressJavaSample.ofText]
         * </details>
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_2)
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
         * SAMPLE: [org.kotools.types.EmailAddressCommonSample.ofTextRegex]
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
         * SAMPLE: [org.kotools.types.EmailAddressJavaSample.ofTextRegex]
         * </details>
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_2)
        @JvmStatic
        public fun of(text: String, regex: EmailAddressRegex): EmailAddress? =
            if (regex matches text) EmailAddress(text) else null

        /**
         * Creates an instance of [EmailAddress] from the specified [text], or
         * returns `null` if the [text] doesn't match the
         * [default regular expression][EmailAddressRegex.Companion.default].
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @Deprecated(
            "Use the 'of(String)' function instead.",
            ReplaceWith(
                "EmailAddress of text",
                "org.kotools.types.EmailAddress"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_2)
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmSynthetic
        public fun orNull(text: String): EmailAddress? {
            val textType: String? = text::class.simpleName
            error("'orNull($textType)' is deprecated as error.")
        }

        /**
         * Returns an email address from the specified [text], or returns `null`
         * if the [text] doesn't match the specified [regex].
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @Deprecated(
            "Use the 'of(String, EmailAddressRegex)' function instead.",
            ReplaceWith(
                "EmailAddress.of(text, regex)",
                "org.kotools.types.EmailAddress"
            ),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V5_0_2)
        @ExperimentalSince(KotoolsTypesVersion.V5_0_1)
        @JvmSynthetic
        public fun orNull(
            text: String,
            regex: EmailAddressRegex
        ): EmailAddress? {
            val textType: String? = text::class.simpleName
            val regexType: String? = regex::class.simpleName
            error("'orNull($textType, $regexType)' is deprecated as error.")
        }

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
            val regex: EmailAddressRegex = EmailAddressRegex.default()
            return this.orThrow(text, regex)
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
            require(regex matches text) {
                ExceptionMessage.invalidEmailAddress(text)
            }
            return EmailAddress(text)
        }
    }
}
