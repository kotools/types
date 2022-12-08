package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/**
 * Returns this string as a [NotBlankString], or [IllegalArgumentException] if
 * this string is blank.
 */
@SinceKotools(Types, "3.2")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotools(Types, "3.2")
public value class NotBlankString
private constructor(private val value: String) : Comparable<NotBlankString> {
    /**
     * Compares this string lexicographically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    /** Returns this value as a [String]. */
    override fun toString(): String = value

    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.toSuccessfulResult(::NotBlankString)
            ?: Result.failure(
                IllegalArgumentException("Given string shouldn't be blank.")
            )
    }
}

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    private val delegate: KSerializer<String> = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotBlankString) {
        val stringValue: String = value.toString()
        delegate.serialize(encoder, stringValue)
    }

    override fun deserialize(decoder: Decoder): NotBlankString = delegate
        .deserialize(decoder)
        .toNotBlankString()
        .getOrThrow()
}
