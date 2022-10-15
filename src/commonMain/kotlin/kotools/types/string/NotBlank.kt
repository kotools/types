package kotools.types.string

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes
import kotools.types.tryOrNull
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotlin.jvm.JvmInline

// ---------- Builders ----------

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
@Throws(NotBlankString.ConstructionError::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/**
 * Returns this value as a [NotBlankString], or returns `null` if this value is
 * blank.
 */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    tryOrNull { NotBlankString(this) }

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
 * Parent of classes responsible for holding strings that have at least one
 * character, excluding whitespaces.
 *
 * @constructor Returns the [value] as a [NotBlankString], or throws an
 * [NotBlankString.ConstructionError] if the [value] is blank.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("1.2")
public value class NotBlankString constructor(
    /** The value to hold. */
    public val value: String
) : Comparable<NotBlankString> {
    init {
        value.takeIf(String::isNotBlank) ?: throw ConstructionError()
    }

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

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
        NotBlankString(value + other)

    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /**
         * Returns the [value] as a [NotBlankString], or returns `null` if the
         * [value] is blank.
         */
        @Deprecated(
            "Use the NotBlankStringOrNull function instead.",
            ReplaceWith(
                "NotBlankStringOrNull(value)",
                "kotools.types.string.NotBlankStringOrNull"
            )
        )
        public infix fun orNull(value: String): NotBlankString? =
            value.toNotBlankStringOrNull()
    }

    /** Error thrown when creating a [NotBlankString] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError : IllegalArgumentException(
        "NotBlankString doesn't accept blank strings."
    )
}

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    private val delegate: KSerializer<String> = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotBlankString): Unit =
        delegate.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder): NotBlankString =
        delegate.deserialize(decoder).toNotBlankString()
}
