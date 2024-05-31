package kotools.types.text

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
import kotools.types.internal.serializationError
import kotools.types.internal.simpleNameOf
import kotools.types.internal.stringSerializer
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

/**
 * Returns this string as an encapsulated [NotBlankString], or returns an
 * encapsulated [IllegalArgumentException] if this string is
 * [blank][String.isBlank].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun String.toNotBlankString(): Result<NotBlankString> = runCatching {
    requireNotNull(NotBlankString of this) { ErrorMessage.blankString }
}

/**
 * Represents a string that has at least one character excluding whitespaces.
 *
 * You can use the [toNotBlankString] function for creating an instance of this
 * type.
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
 * val string: NotBlankString = "hello world".toNotBlankString()
 *     .getOrThrow()
 * val encoded: String = Json.encodeToString(string)
 * println(encoded) // "hello world"
 * val decoded: NotBlankString = Json.decodeFromString(encoded)
 * println(decoded == string) // true
 * ```
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NotBlankStringSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public value class NotBlankString private constructor(
    private val value: String
) : Comparable<NotBlankString> {
    /** Returns the length of this string. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Compares this string alphabetically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    @Since(KotoolsTypesVersion.V4_1_0)
    override infix fun compareTo(other: NotBlankString): Int =
        "$this".compareTo("$other")

    /**
     * Concatenates this string with the string representation of the [other]
     * object.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val text: NotBlankString = "hello".toNotBlankString()
     *     .getOrThrow()
     * val message: NotBlankString = text + " world"
     * println(message) // hello world
     * ```
     *
     * The [NotBlankString] type being an
     * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
     * this function is not available yet for Java users.
     */
    @ExperimentalKotoolsTypesApi
    @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
    @JvmSynthetic
    public operator fun plus(other: Any): NotBlankString = value.plus("$other")
        .toNotBlankString()
        .getOrThrow()

    /** Returns this string as a [String]. */
    override fun toString(): String = value

    /** Contains static declarations for the [NotBlankString] type. */
    public companion object {
        /**
         * Creates a [NotBlankString] from the string representation of the
         * specified [value], or throws an [IllegalArgumentException] if its
         * string representation is [blank][String.isBlank].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: NotBlankStringCompanionKotlinSample.create.md
         *
         * The [NotBlankString] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [NotBlankString.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [value].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        @JvmSynthetic
        public fun create(value: Any): NotBlankString {
            val text: String = value.toString()
            val isValid: Boolean = text.isNotBlank()
            require(isValid) { ErrorMessage.blankString }
            return NotBlankString(text)
        }

        /**
         * Creates a [NotBlankString] from the string representation of the
         * specified [value], or returns `null` if its string representation is
         * [blank][String.isBlank].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val text: NotBlankString? =
         *     NotBlankString.createOrNull("Kotools Types")
         * println(text) // Kotools Types
         * ```
         *
         * The [NotBlankString] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [NotBlankString.Companion.create] function for
         * throwing an exception instead of returning `null` in case of invalid
         * [value].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        @JvmSynthetic
        public fun createOrNull(value: Any): NotBlankString? {
            val text: String = value.toString()
            val isValid: Boolean = text.isNotBlank()
            return if (isValid) NotBlankString(text)
            else null
        }

        @JvmSynthetic
        internal infix fun of(value: String): NotBlankString? =
            if (value.isBlank()) null
            else NotBlankString(value)
    }
}

@InternalKotoolsTypesApi
internal object NotBlankStringSerializer :
    KSerializer<NotBlankString> by stringSerializer(
        NotBlankStringDeserializationStrategy
    )

@InternalKotoolsTypesApi
private object NotBlankStringDeserializationStrategy :
    DeserializationStrategy<NotBlankString> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<NotBlankString>()
        val serialName = "${KotoolsTypesPackage.Text}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
    }

    override fun deserialize(decoder: Decoder): NotBlankString = decoder
        .decodeString()
        .toNotBlankString()
        .getOrNull()
        ?: serializationError(ErrorMessage.blankString)
}
