package kotools.types.experimental

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.deserializationError
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import kotools.types.internal.text.SpecialChar
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [String] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * ```kotlin
 * val address: EmailAddress =
 *     checkNotNull(EmailAddress from "contact@kotools.org")
 * val encoded: String = Json.encodeToString(address)
 * println(encoded) // "contact@kotools.org"
 * val decoded: EmailAddress = Json.decodeFromString(encoded)
 * println(decoded == address) // true
 * ```
 * </details>
 *
 * @constructor Creates an email address from the specified [text], or throws an
 * [IllegalArgumentException] if the [text] doesn't match the corresponding
 * [regular expression][EmailAddress.Companion.regex].
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * ```kotlin
 * val address = EmailAddress("contact@kotools.org")
 * println(address) // contact@kotools.org
 * ```
 *
 * Here's an example of calling this constructor from Java code:
 *
 * ```java
 * final EmailAddress address = EmailAddress("contact@kotools.org");
 * System.out.println(address); // contact@kotools.org
 * ```
 *
 * You can use the [EmailAddress.Companion.from] function for returning `null`
 * instead of throwing an exception in case of invalid [text].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@Serializable(EmailAddressSerializer::class)
public class EmailAddress(private val text: String) {
    init {
        require(text matches regex) { creationErrorMessage }
    }

    // -------------------------- Structural equality --------------------------

    /**
     * Returns `true` if the [other] object is an [EmailAddress] having the same
     * string representation as this email address, or returns `false`
     * otherwise.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val first: EmailAddress =
     *     checkNotNull(EmailAddress from "contact@kotools.org")
     * val second: EmailAddress = checkNotNull(EmailAddress from "$first")
     * val result: Boolean = first == second // or first.equals(second)
     * println(result) // true
     * ```
     *
     * Please note that this function is not available yet for Java users.
     */
    @JvmSynthetic
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other == null -> false
        else -> other is EmailAddress && this.text == other.text
    }

    /**
     * Returns a hash code value for this email address.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val first: EmailAddress =
     *     checkNotNull(EmailAddress from "contact@kotools.org")
     * val second: EmailAddress = checkNotNull(EmailAddress from "$first")
     * val result: Boolean = first.hashCode() == second.hashCode()
     * println(result) // true
     * ```
     *
     * Please note that this function is not available yet for Java users.
     */
    @JvmSynthetic
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int = hashCodeOf(text)

    // ------------------------------ Converters -------------------------------

    /**
     * Returns the string representation of this email address.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val address: EmailAddress =
     *     checkNotNull(EmailAddress from "contact@kotools.org")
     * val message = "$address" // or address.toString()
     * println(message) // contact@kotools.org
     * ```
     *
     * Please note that this function is not available yet for Java users.
     */
    @JvmSynthetic
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * The regular expression that an [EmailAddress] should match.
         *
         * The underlying pattern is `^\S+@\S+\.\S+$`.
         *
         * Here's the explanation associated to each symbol used in this regular
         * expression:
         * - `^` **Beginning.** Matches the beginning of the string, or the
         * beginning of a line if the multiline flag (m) is enabled.
         * - `\S` **Not whitespace.** Matches any character that is not a
         * whitespace character (spaces, tabs, line breaks).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `@` **Character.** Matches a "@" character (char code 64).
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (m) is enabled.
         *
         * Here's an example of calling this property from Kotlin code:
         *
         * ```kotlin
         * println(EmailAddress.regex) // ^\S+@\S+\.\S+$
         * ```
         *
         * Please note that this property is not available yet for Java users.
         */
        @get:JvmSynthetic
        public val regex: Regex =
            Regex("^\\S+${SpecialChar.AtSign}\\S+\\.\\S+\$")

        @get:JvmSynthetic
        internal val creationErrorMessage: ErrorMessage
            get() = ErrorMessage("Email address should match $regex")

        /**
         * Creates an email address from the specified [text], or returns `null`
         * if the [text] doesn't match the corresponding
         * [regular expression][EmailAddress.Companion.regex].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val address: EmailAddress? = EmailAddress from "contact@kotools.org"
         * println(address) // contact@kotools.org
         * ```
         *
         * Please note that this function is not available yet for Java users.
         *
         * You can use the `EmailAddress(String)` constructor for throwing an
         * exception instead of returning `null` in case of invalid [text].
         */
        @JvmSynthetic
        public infix fun from(text: String): EmailAddress? =
            if (text matches regex) EmailAddress(text)
            else null
    }
}

@ExperimentalKotoolsTypesApi
private object EmailAddressSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor by lazy {
        val type: String = simpleNameOf<EmailAddress>()
        val serialName = "${KotoolsTypesPackage.Experimental}.$type"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    }

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress {
        val text: String = decoder.decodeString()
        return EmailAddress.from(text)
            ?: deserializationError<EmailAddress>(text)
    }
}
