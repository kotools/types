package kotools.types.web

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.deserializationError
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import kotools.types.internal.stringSerializer
import kotools.types.internal.text.SpecialChar
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * You can use the [EmailAddress.Companion.create] or the
 * [EmailAddress.Companion.createOrNull] functions for creating an instance of
 * this type.
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
 * val address: EmailAddress = EmailAddress.create("contact@kotools.org")
 * val encoded: String = Json.encodeToString(address)
 * println(encoded) // "contact@kotools.org"
 * val decoded: EmailAddress = Json.decodeFromString(encoded)
 * println(decoded == address) // true
 * ```
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@Serializable(EmailAddressSerializer::class)
public class EmailAddress private constructor(private val text: String) {
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
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this property from Kotlin code:
         *
         * ```kotlin
         * println(EmailAddress.regex) // ^\S+@\S+\.\S+$
         * ```
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
         * ```java
         * final Regex regex = EmailAddress.Companion.getRegex();
         * System.out.println(regex); // ^\S+@\S+\.\S+$
         * ```
         * </details>
         */
        public val regex: Regex by lazy {
            Regex("^\\S+${SpecialChar.AtSign}\\S+\\.\\S+\$")
        }

        /**
         * Creates an email address from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't match the
         * corresponding [regular expression][EmailAddress.Companion.regex].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val address: EmailAddress =
         *     EmailAddress.create("contact@kotools.org")
         * println(address) // contact@kotools.org
         * ```
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
         * ```java
         * final EmailAddress address =
         *         EmailAddress.Companion.create("contact@kotools.org");
         * System.out.println(address); // contact@kotools.org
         * ```
         * </details>
         * <br>
         *
         * You can use the [EmailAddress.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [text].
         */
        public fun create(text: String): EmailAddress {
            require(text matches regex) { creationErrorMessage(text) }
            return EmailAddress(text)
        }

        @JvmSynthetic
        internal fun creationErrorMessage(value: String): ErrorMessage =
            ErrorMessage(
                "Email address should match $regex (tried with $value)"
            )

        /**
         * Creates an email address from the specified [text], or returns `null`
         * if the [text] doesn't match the corresponding
         * [regular expression][EmailAddress.Companion.regex].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val address: EmailAddress? =
         *     EmailAddress.createOrNull("contact@kotools.org")
         * println(address) // contact@kotools.org
         * ```
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
         * ```java
         * final EmailAddress address =
         *         EmailAddress.Companion.createOrNull("contact@kotools.org");
         * System.out.println(address); // contact@kotools.org
         * ```
         * </details>
         * <br>
         *
         * You can use the [EmailAddress.Companion.create] function for throwing
         * an exception instead of returning `null` in case of invalid [text].
         */
        public fun createOrNull(text: String): EmailAddress? =
            if (text matches regex) EmailAddress(text)
            else null
    }

    // -------------------------- Structural equality --------------------------

    /**
     * Returns `true` if the [other] object is an [EmailAddress] having the same
     * string representation as this email address, or returns `false`
     * otherwise.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val first: EmailAddress = EmailAddress.create("contact@kotools.org")
     * val second: EmailAddress = EmailAddress.create("$first")
     * val result: Boolean = first == second // or first.equals(second)
     * println(result) // true
     * ```
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
     * ```java
     * final String text = "contact@kotools.org";
     * final EmailAddress first = EmailAddress.Companion.create(text);
     * final EmailAddress second = EmailAddress.Companion.create(text);
     * final boolean result = first.equals(second);
     * System.out.println(result); // true
     * ```
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other == null -> false
        else -> other is EmailAddress && this.text == other.text
    }

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
     * ```kotlin
     * val text = "contact@kotools.org"
     * val first: Int = EmailAddress.create(text)
     *     .hashCode()
     * val second: Int = EmailAddress.create("$first")
     *     .hashCode()
     * val result: Boolean = first == second
     * println(result) // true
     * ```
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
     * ```java
     * final String text = "contact@kotools.org";
     * final EmailAddress first = EmailAddress.Companion.create(text);
     * final EmailAddress second = EmailAddress.Companion.create(text);
     * final int firstHashCode = first.hashCode();
     * final int secondHashCode = second.hashCode();
     * final boolean result = firstHashCode == secondHashCode;
     * System.out.println(result); // true
     * ```
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int = hashCodeOf(text)

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
     * ```kotlin
     * val address: EmailAddress = EmailAddress.create("contact@kotools.org")
     * val message = "$address" // or address.toString()
     * println(message) // contact@kotools.org
     * ```
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
     * ```java
     * final EmailAddress address =
     *         EmailAddress.Companion.create("contact@kotools.org");
     * final String message = address.toString();
     * System.out.println(message); // contact@kotools.org
     * ```
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = text
}

@ExperimentalKotoolsTypesApi
private object EmailAddressSerializer :
    KSerializer<EmailAddress> by stringSerializer(
        EmailAddressDeserializationStrategy
    )

@ExperimentalKotoolsTypesApi
private object EmailAddressDeserializationStrategy :
    DeserializationStrategy<EmailAddress> {
    override val descriptor: SerialDescriptor by lazy {
        val type: String = simpleNameOf<EmailAddress>()
        val serialName = "${KotoolsTypesPackage.Experimental}.$type"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    }

    override fun deserialize(decoder: Decoder): EmailAddress {
        val text: String = decoder.decodeString()
        return EmailAddress.createOrNull(text)
            ?: deserializationError<EmailAddress>(text)
    }
}
