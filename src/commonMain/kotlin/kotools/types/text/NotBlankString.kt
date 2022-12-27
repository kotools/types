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
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 *
 * See the [toNotBlankString] function for building a [NotBlankString].
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotBlankString private constructor(
    /** The string to hold. */
    public val value: String
) : Comparable<NotBlankString> {
    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.toSuccessfulResult(::NotBlankString)
            ?: Result.failure(NotBlankStringException)
    }

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if it's less than the [other] value, or a positive number if it's greater
     * than the [other] value.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    /** Returns this [value]. */
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
    override val descriptor: SerialDescriptor = "${Package.text}.NotBlankString"
        .toNotBlankString()
        .map { PrimitiveSerialDescriptor(it.value, PrimitiveKind.STRING) }
        .getOrThrow()

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
