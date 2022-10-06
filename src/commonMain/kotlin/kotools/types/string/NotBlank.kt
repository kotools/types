package kotools.types.string

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.Holder
import kotools.types.core.HolderCompanion
import kotools.types.core.SinceKotoolsTypes
import kotools.types.number.*
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a [NotBlankString], or throws an
 * [IllegalArgumentException] if this value is blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/**
 * Returns this value as a [NotBlankString], or returns `null` if this value is
 * blank.
 */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    NotBlankString orNull this

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
 * [IllegalArgumentException] if the [value] is blank.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotoolsTypes("1.2")
public value class NotBlankString
@Throws(IllegalArgumentException::class)
constructor(override val value: String) : Comparable<NotBlankString>,
    Holder<String> {
    init {
        require(value.isNotBlank()) {
            "NotBlankString doesn't accept blank strings."
        }
    }

    public companion object :
        HolderCompanion<String, NotBlankString>(::NotBlankString)

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
    @SinceKotoolsTypes("3.0")
    @Throws(IndexOutOfBoundsException::class)
    public operator fun get(index: PositiveIntHolder): Char = value[index.value]

    // ---------- Binary operations ----------

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    @SinceKotoolsTypes("2.0")
    public infix fun compareTo(other: String): Int = value.compareTo(other)

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: NotBlankString): Int =
        compareTo(other.value)

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the [other] object.
     */
    public infix operator fun plus(other: Any?): NotBlankString =
        NotBlankString(value + other)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a [NonZeroInt].
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents zero.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /**
     * Returns this [value] as a [NonZeroInt], or returns `null` if this [value]
     * is not a valid representation of a number or if it represents zero.
     */
    @SinceKotoolsTypes("3.0")
    public fun toNonZeroIntOrNull(): NonZeroInt? = value.toNonZeroIntOrNull()

    /**
     * Returns this [value] as a [PositiveInt].
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly negative number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toPositiveInt(): PositiveInt = value.toPositiveInt()

    /**
     * Returns this [value] as a [PositiveInt], or returns `null` if this
     * [value] is not a valid representation of a number or if it represents a
     * strictly negative number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toPositiveIntOrNull(): PositiveInt? = value.toPositiveIntOrNull()

    /**
     * Returns this [value] as a [StrictlyPositiveInt].
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or returns `null` if
     * this [value] is not a valid representation of a number or if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        value.toStrictlyPositiveIntOrNull()

    /**
     * Returns this [value] as a [NegativeInt].
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly positive number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    /**
     * Returns this [value] as a [NegativeInt], or returns `null` if this
     * [value] is not a valid representation of a number or if it represents a
     * strictly positive number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toNegativeIntOrNull(): NegativeInt? = value.toNegativeIntOrNull()

    /**
     * Returns this [value] as a [StrictlyNegativeInt].
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a positive number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or returns `null` if
     * this [value] is not a valid representation of a number or if it
     * represents a positive number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        value.toStrictlyNegativeIntOrNull()
}

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    private val delegate: KSerializer<String> = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotBlankString): Unit =
        delegate.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder): NotBlankString =
        delegate.deserialize(decoder).toNotBlankString()
}
