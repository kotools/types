package kotools.types.string

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.*
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.strictlyPositive
import kotlin.jvm.JvmInline

// ---------- Builders ----------

private fun notBlankString(
    value: String
): KotoolsTypesBuilderResult<NotBlankString> = value.takeIf(String::isNotBlank)
    ?.toSuccessfulResult(::NotBlankStringImplementation)
    ?: value.shouldBe("not blank"::toNotBlankString)

/**
 * Returns the [value] as a [NotBlankString], or throws an
 * [IllegalArgumentException] if the [value] is blank.
 */
@SinceKotoolsTypes("3.2")
@Throws(IllegalArgumentException::class)
public fun notBlankStringOrThrow(value: String): NotBlankString =
    notBlankString(value)
        .onError { throw it }

/**
 * Returns the [value] as a [NotBlankString], or throws an
 * [NotBlankString.ConstructionError] if the [value] is blank.
 */
@Deprecated(
    "Use the notBlankStringOrThrow function instead. Will be an error in v3.3.",
    ReplaceWith(
        "notBlankStringOrThrow(value)",
        "${Package.string}.notBlankStringOrThrow"
    )
)
@SinceKotoolsTypes("1.2")
@Suppress("DEPRECATION")
@Throws(NotBlankString.ConstructionError::class)
public fun NotBlankString(value: String): NotBlankString =
    value.toNotBlankString()

/**
 * Returns the [value] as a [NotBlankString], or returns `null` if the [value]
 * is blank.
 */
@Suppress("FunctionName")
@SinceKotoolsTypes("3.0")
public fun NotBlankStringOrNull(value: String): NotBlankString? =
    value.toNotBlankStringOrNull()

/**
 * Returns this value as a [NotBlankString], or throws an
 * [NotBlankString.ConstructionError] if this value is blank.
 */
@SinceKotoolsTypes("1.2")
@Suppress("DEPRECATION")
@Throws(NotBlankString.ConstructionError::class)
public fun String.toNotBlankString(): NotBlankString =
    toNotBlankStringOrNull() ?: throw NotBlankString.ConstructionError()

/**
 * Returns this value as a [NotBlankString], or returns `null` if this value is
 * blank.
 */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    takeIf(String::isNotBlank)
        ?.let(::NotBlankStringImplementation)

// ---------- Binary operations ----------

/**
 * Compares this value lexicographically with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("2.0")
public infix operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.value)

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("1.2")
public sealed interface NotBlankString : Comparable<NotBlankString> {
    /** The value to hold. */
    public val value: String

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = strictlyPositive int value.length

    // ---------- Positional access operations ----------

    /** Returns the first character of this [value]. */
    public val first: Char get() = value[0]

    /**
     * Returns the character of this [value] at the specified [index].
     * Throws an [IndexOutOfBoundsException] if the [index] is out of bounds,
     * except in Kotlin/JS where the behavior is unspecified.
     */
    @Throws(IndexOutOfBoundsException::class)
    public operator fun get(index: PositiveInt): Char = value[index.value]

    // ---------- Binary operations ----------

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    @SinceKotoolsTypes("2.0")
    public operator fun compareTo(other: String): Int = value.compareTo(other)

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: NotBlankString): Int = compareTo(other.value)

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the [other] object.
     */
    public operator fun plus(other: Any?): NotBlankString =
        notBlankStringOrThrow(value + other)

    /** Error thrown when creating a [NotBlankString] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead. Will be an error in v3.3.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotoolsTypes("3.0")
    public class ConstructionError : IllegalArgumentException(
        "NotBlankString doesn't accept blank strings."
    )
}

@JvmInline
private value class NotBlankStringImplementation(
    override val value: String
) : NotBlankString {
    // ---------- Conversions ----------

    override fun toString(): String = value
}

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    private val delegate: KSerializer<String> = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotBlankString): Unit =
        delegate.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder): NotBlankString =
        delegate.deserialize(decoder).toNotBlankString()
}
