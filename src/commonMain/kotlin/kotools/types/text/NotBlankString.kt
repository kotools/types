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

/**
 * Returns this string as an encapsulated [NotBlankString], or returns an
 * encapsulated [IllegalArgumentException] if this string is
 * [blank][String.isBlank].
 */
@SinceKotoolsTypes("4.0")
public fun String.toNotBlankString(): Result<NotBlankString> =
    runCatching { NotBlankString(this) }

/**
 * Returns this string as a [NotBlankString], or returns `null` if this string
 * is [blank][String.isBlank].
 *
 * Here's some usage examples:
 *
 * ```kotlin
 * var result: NotBlankString? = "hello world".toNotBlankStringOrNull()
 * println(result) // hello world
 *
 * result = "  ".toNotBlankStringOrNull()
 * println(null) // null
 * ```
 *
 * You can use the [toNotBlankStringOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null` when this string is
 * [blank][String.isBlank].
 */
@ExperimentalSinceKotoolsTypes("4.3.1")
@ExperimentalTextApi
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    NotBlankString.of(this)

/**
 * Returns this string as a [NotBlankString], or throws an
 * [IllegalArgumentException] if this string is [blank][String.isBlank].
 *
 * Here's some usage examples:
 *
 * ```kotlin
 * var result: NotBlankString = "hello world".toNotBlankStringOrThrow()
 * println(result) // hello world
 *
 * "  ".toNotBlankStringOrThrow() // IllegalArgumentException
 * ```
 *
 * You can use the [toNotBlankStringOrNull] function for returning `null`
 * instead of throwing an [IllegalArgumentException] when this string is
 * [blank][String.isBlank].
 */
@ExperimentalSinceKotoolsTypes("4.3.1")
@ExperimentalTextApi
public fun String.toNotBlankStringOrThrow(): NotBlankString {
    val value: NotBlankString? = NotBlankString.of(this)
    return requireNotNull(value) { NotBlankStringException.message }
}

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotBlankString internal constructor(
    private val value: String
) : Comparable<NotBlankString> {
    /** Returns the length of this string. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    init {
        require(value.isNotBlank()) { NotBlankStringException.message }
    }

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

    /** Contains static declarations for the [NotBlankString] type. */
    public companion object {
        /**
         * Returns the given [string] as a [NotBlankString], or returns `null`
         * if the [string] is [blank][CharSequence.isBlank].
         *
         * ```kotlin
         * var result: NotBlankString? = NotBlankString.of("hello world")
         * println(result) // hello world
         *
         * result = NotBlankString.of(" ")
         * println(result) // null
         * ```
         */
        @ExperimentalSinceKotoolsTypes("4.3.2")
        @ExperimentalTextApi
        public fun of(string: String): NotBlankString? =
            if (string.isBlank()) null else NotBlankString(string)
    }
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

internal object NotBlankStringException : IllegalArgumentException() {
    override val message: String = "Given string shouldn't be blank."
}
