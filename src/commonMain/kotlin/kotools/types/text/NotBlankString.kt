package kotools.types.text

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.asStrictlyPositiveInt
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotBlankString private constructor(
    private val value: String
) : Comparable<NotBlankString> {
    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.toSuccessfulResult(::NotBlankString)
            ?: Result.failure(NotBlankStringException)
    }

    /** Returns the length of this string. */
    public val length: StrictlyPositiveInt
        get() = value.length.asStrictlyPositiveInt.getOrThrow()

    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    /** Returns this string as a [String]. */
    override fun toString(): String = value
}

/**
 * Returns this string as a [NotBlankString], or returns an
 * [IllegalArgumentException] if this string is blank.
 */
@SinceKotoolsTypes("4.0")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "${Package.text}.NotBlankString",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: NotBlankString): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): NotBlankString = decoder
        .decodeString()
        .toNotBlankString()
        .getOrNull()
        ?: throw SerializationException(NotBlankStringException)
}

private object NotBlankStringException :
    IllegalArgumentException("Given string shouldn't be blank.")
