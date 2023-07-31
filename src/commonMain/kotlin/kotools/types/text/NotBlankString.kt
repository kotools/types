package kotools.types.text

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalTextApi
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName

/**
 * Returns this string as an encapsulated [NotBlankString], or returns an
 * encapsulated [IllegalArgumentException] if this string is
 * [blank][String.isBlank].
 */
@SinceKotoolsTypes("4.0")
@JvmName("of")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this

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
            ?.runCatching { NotBlankString(this) }
            ?: Result.failure(NotBlankStringException)
    }

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
    @SinceKotoolsTypes("4.1")
    override infix fun compareTo(other: NotBlankString): Int =
        "$this".compareTo("$other")

    /** Returns this string as a [String]. */
    override fun toString(): String = value
}

/** Concatenates this string with the [other] one. */
@ExperimentalSinceKotoolsTypes("4.2")
@ExperimentalTextApi
public operator fun NotBlankString.plus(other: String): NotBlankString = "$this"
    .plus(other)
    .toNotBlankString()
    .getOrThrow()

/** Concatenates this string with the [other] one. */
@ExperimentalSinceKotoolsTypes("4.2")
@ExperimentalTextApi
public operator fun NotBlankString.plus(other: NotBlankString): NotBlankString =
    plus("$other")

/** Concatenates this string with the [other] character. */
@ExperimentalSinceKotoolsTypes("4.2")
@ExperimentalTextApi
public operator fun NotBlankString.plus(other: Char): NotBlankString =
    plus("$other")

/** Concatenates this character with the [other] string. */
@ExperimentalSinceKotoolsTypes("4.2")
@ExperimentalTextApi
public operator fun Char.plus(other: NotBlankString): NotBlankString =
    plus("$other")
        .toNotBlankString()
        .getOrThrow()

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
