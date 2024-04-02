package org.kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmStatic

private const val FINAL_WARNING: String = "RedundantModalityModifier"

/**
 * Represents an [email address](https://en.wikipedia.org/wiki/Email_address).
 *
 * You can use the [EmailAddress.Companion.fromString] or the
 * [EmailAddress.Companion.fromStringOrNull] functions for creating an instance
 * of this type.
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
 * SAMPLE: EmailAddressKotlinSample.serialization.md
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@Serializable(with = EmailAddressAsStringSerializer::class)
public class EmailAddress private constructor(private val value: String) {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [EmailAddress] and
     * has the same string representation as this email address, or returns
     * `false` otherwise.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: EmailAddressKotlinSample.equals_override.md
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
     * SAMPLE: EmailAddressJavaSample.equals_override.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
    final override fun equals(other: Any?): Boolean =
        other is EmailAddress && this.value == other.value

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
     * SAMPLE: EmailAddressKotlinSample.hashCode_override.md
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
     * SAMPLE: EmailAddressJavaSample.hashCode_override.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
    final override fun hashCode(): Int = hashCodeOf(value)

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
     * SAMPLE: EmailAddressKotlinSample.toString_override.md
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
     * SAMPLE: EmailAddressJavaSample.toString_override.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
    final override fun toString(): String = value

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [EmailAddress] type. */
    public companion object {
        /**
         * The pattern that an email address should match.
         *
         * The underlying value is `^\S+@\S+\.\S+$`.
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

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or throws an [IllegalArgumentException] if
         * the string representation of [value] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.fromString_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromString_Any.md
         * </details>
         * <br>
         *
         * You can use the [fromStringOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun fromString(value: Any): EmailAddress =
            fromString(value, PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value].
         * Throws an [IllegalArgumentException] if the string representation of
         * [value] doesn't match the string representation of the specified
         * [pattern], or if the string representation of [pattern] doesn't match
         * the [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.fromString_Any_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromString_Any_Any.md
         * </details>
         * <br>
         *
         * You can use the [fromStringOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value] or
         * [pattern].
         */
        @JvmStatic
        public fun fromString(value: Any, pattern: Any): EmailAddress {
            val patternAsString: String = pattern.toString()
            val defaultRegex = Regex(PATTERN)
            require(patternAsString matches defaultRegex) {
                InvalidEmailAddressPattern(pattern)
            }
            return fromString(value, patternAsString)
        }

        private fun fromString(value: Any, pattern: String): EmailAddress {
            val address: EmailAddress? = fromStringOrNull(value, pattern)
            return requireNotNull(address) {
                InvalidEmailAddress(value, pattern)
            }
        }

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value], or returns `null` if the string
         * representation of [value] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.fromStringOrNull_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromStringOrNull_Any.md
         * </details>
         * <br>
         *
         * You can use the [fromString] function for throwing an exception
         * instead of returning `null` in case of invalid [value].
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any): EmailAddress? =
            fromStringOrNull(value, PATTERN)

        /**
         * Creates an instance of [EmailAddress] from the string representation
         * of the specified [value].
         * Returns `null` if the string representation of [value] doesn't match
         * the string representation of the specified [pattern], or if the
         * string representation of [pattern] doesn't match the
         * [default pattern][PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: EmailAddressCompanionKotlinSample.fromStringOrNull_Any_Any.md
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
         * SAMPLE: EmailAddressCompanionJavaSample.fromStringOrNull_Any_Any.md
         * </details>
         * <br>
         *
         * You can use the [fromString] function for throwing an exception
         * instead of returning `null` in case of invalid [value] or [pattern].
         */
        @JvmStatic
        public fun fromStringOrNull(value: Any, pattern: Any): EmailAddress? {
            val patternAsString: String = pattern.toString()
            val defaultRegex = Regex(PATTERN)
            if (!patternAsString.matches(defaultRegex)) return null
            return fromStringOrNull(value, patternAsString)
        }

        private fun fromStringOrNull(
            value: Any,
            pattern: String
        ): EmailAddress? {
            val valueAsString: String = value.toString()
            val regex = Regex(pattern)
            return if (valueAsString matches regex) EmailAddress(valueAsString)
            else null
        }
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressAsStringSerializer : KSerializer<EmailAddress> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "org.kotools.types.EmailAddress",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: EmailAddress): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): EmailAddress {
        val value: String = decoder.decodeString()
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        if (null != address) return address
        val error = InvalidEmailAddress(value)
        throw SerializationException("$error")
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class InvalidEmailAddress(
    private val value: Any,
    private val pattern: Any = EmailAddress.PATTERN
) {
    override fun equals(other: Any?): Boolean = other is InvalidEmailAddress
            && this.value == other.value
            && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(value, pattern)

    override fun toString(): String =
        "\"$value\" is an invalid email address. " +
                "It should match the following pattern: $pattern"
}

internal class InvalidEmailAddressPattern(private val pattern: Any) {
    override fun equals(other: Any?): Boolean =
        other is InvalidEmailAddressPattern && this.pattern == other.pattern

    override fun hashCode(): Int = hashCodeOf(pattern)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    override fun toString(): String =
        "$pattern is an invalid email address pattern. " +
                "It should match the following one: ${EmailAddress.PATTERN}"
}
