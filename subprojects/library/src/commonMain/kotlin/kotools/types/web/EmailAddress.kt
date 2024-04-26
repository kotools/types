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
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.deserializationError
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import kotools.types.internal.stringSerializer
import org.kotools.types.internal.DeprecatedAsErrorSince
import org.kotools.types.internal.DeprecatedAsWarningSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

private const val DEPRECATED_WARNING: String = "DEPRECATION"
private const val FINAL_WARNING: String = "RedundantModalityModifier"

private const val NEW_TYPE_QUALIFIED_NAME: String =
    "org.kotools.types.EmailAddress"

private const val TYPE_DEPRECATION_MESSAGE: String = "A better " +
        "representation of email addresses exists in the `org.kotools.types` " +
        "package. This one will be removed in v4.7."

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
 * val address = EmailAddress("contact@kotools.org")
 * val encoded: String = Json.encodeToString(address)
 * println(encoded) // "contact@kotools.org"
 * val decoded: EmailAddress = Json.decodeFromString(encoded)
 * println(decoded == address) // true
 * ```
 * </details>
 *
 * @constructor Creates an email address from the specified [value], or throws
 * an [IllegalArgumentException] if the [value] doesn't match the corresponding
 * [regular expression][EmailAddress.Companion.regex].
 *
 * <br>
 * <details open>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * ```kotlin
 * val address = EmailAddress("contact@kotools.org")
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
 * Here's an example of calling this constructor from Java code:
 *
 * ```java
 * final EmailAddress address = new EmailAddress("contact@kotools.org");
 * System.out.println(address); // contact@kotools.org
 * ```
 * </details>
 */
@Deprecated(TYPE_DEPRECATION_MESSAGE)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_5_1)
@ExperimentalKotoolsTypesApi
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(EmailAddressSerializer::class)
public class EmailAddress internal constructor(private val value: String) {
    init {
        require(value matches regex) { ErrorMessage.invalidEmailAddress(value) }
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
     * val value = "contact@kotools.org"
     * val first = EmailAddress(value)
     * val second = EmailAddress(value)
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
     * final String value = "contact@kotools.org";
     * final EmailAddress first = new EmailAddress(value);
     * final EmailAddress second = new EmailAddress(value);
     * final boolean result = first.equals(second);
     * System.out.println(result); // true
     * ```
     * </details>
     */
    @Suppress(DEPRECATED_WARNING, FINAL_WARNING)
    final override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other == null -> false
        else -> other is EmailAddress && this.value == other.value
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
     * val value = "contact@kotools.org"
     * val first: Int = EmailAddress(value)
     *     .hashCode()
     * val second: Int = EmailAddress(value)
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
     * final String value = "contact@kotools.org";
     * final EmailAddress first = new EmailAddress(value);
     * final EmailAddress second = new EmailAddress(value);
     * final int firstHashCode = first.hashCode();
     * final int secondHashCode = second.hashCode();
     * final boolean result = firstHashCode == secondHashCode;
     * System.out.println(result); // true
     * ```
     */
    @Suppress(FINAL_WARNING)
    final override fun hashCode(): Int = hashCodeOf(value)

    // ------------------------------ Conversions ------------------------------

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
     * val address = EmailAddress("contact@kotools.org")
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
     * final EmailAddress address = new EmailAddress("contact@kotools.org");
     * final String message = address.toString();
     * System.out.println(message); // contact@kotools.org
     * ```
     * </details>
     */
    @Suppress(DEPRECATED_WARNING, FINAL_WARNING)
    final override fun toString(): String = value

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * The regular expression that an [EmailAddress] should match.
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
         * Here's an example of calling this property from Kotlin code:
         *
         * ```kotlin
         * println("contact@kotools.org" matches EmailAddress.regex) // true
         * ```
         *
         * The [Regex] type being unavailable on Java, this property is not
         * available for this language.
         */
        @get:JvmSynthetic
        public val regex: Regex = Regex("^\\S+@\\S+\\.\\S+\$")

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
         */
        @Deprecated(
            TYPE_DEPRECATION_MESSAGE,
            ReplaceWith("$NEW_TYPE_QUALIFIED_NAME.fromString(text)"),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_1)
        @Suppress(DEPRECATED_WARNING)
        public fun create(text: String): EmailAddress {
            require(text matches regex) {
                ErrorMessage.invalidEmailAddress(text)
            }
            return EmailAddress(text)
        }

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
         */
        @Deprecated(
            TYPE_DEPRECATION_MESSAGE,
            ReplaceWith("$NEW_TYPE_QUALIFIED_NAME.fromStringOrNull(text)"),
            DeprecationLevel.ERROR
        )
        @DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_1)
        @Suppress(DEPRECATED_WARNING)
        public fun createOrNull(text: String): EmailAddress? =
            if (text matches regex) EmailAddress(text)
            else null
    }
}

@ExperimentalKotoolsTypesApi
@InternalKotoolsTypesApi
@Suppress(DEPRECATED_WARNING)
internal object EmailAddressSerializer :
    KSerializer<EmailAddress> by stringSerializer(
        EmailAddressDeserializationStrategy
    )

@ExperimentalKotoolsTypesApi
@Suppress(DEPRECATED_WARNING)
private object EmailAddressDeserializationStrategy :
    DeserializationStrategy<EmailAddress> {
    @OptIn(InternalKotoolsTypesApi::class)
    override val descriptor: SerialDescriptor by lazy {
        val type: String = simpleNameOf<EmailAddress>()
        val serialName = "${KotoolsTypesPackage.Web}.$type"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    override fun deserialize(decoder: Decoder): EmailAddress {
        val text: String = decoder.decodeString()
        return kotlin.runCatching { EmailAddress(text) }
            .getOrNull()
            ?: deserializationError<EmailAddress>(text)
    }
}
