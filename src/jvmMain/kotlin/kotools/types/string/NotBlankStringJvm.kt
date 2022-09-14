package kotools.types.string

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.*

// ---------- Comparisons ----------

/**
 * Compares this value lexicographically with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("2.0")
public infix operator fun String.compareTo(other: NotBlankStringJvm): Int =
    compareTo(other.value)

// ---------- Conversions ----------

/**
 * Returns this value as a not blank string, or throws an
 * [IllegalArgumentException] if this value is blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankStringJvm(): NotBlankStringJvm =
    NotBlankStringJvm(this)

/**
 * Returns this value as a not blank string, or returns `null` if this value is
 * blank.
 */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringJvmOrNull(): NotBlankStringJvm? =
    NotBlankStringJvm orNull this

/**
 * Represents strings containing at least 1 character, excluding whitespaces.
 *
 * @constructor Returns the [value] as a not blank string, or throws an
 * [IllegalArgumentException] if the [value] is blank.
 */
@JvmInline
@Serializable(NotBlankStringJvm.Serializer::class)
@SinceKotoolsTypes("1.2")
public value class NotBlankStringJvm
@Throws(IllegalArgumentException::class)
public constructor(public val value: String) : Comparable<String> {
    init {
        require(value.isNotBlank()) {
            val type: String = this::class.simpleName!!
            "$type doesn't accept blank values."
        }
    }

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveIntJvm
        get() = StrictlyPositiveIntJvm(value.length)

    // ---------- Positional access operations ----------

    /** Returns the first character of this [value]. */
    public val first: Char get() = value[0]

    /**
     * Returns the character of this [value] at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveIntJvm): Char =
        value[index.value]

    /**
     * Returns the character of this [value] at the specified [index], or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveIntJvm): Char? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    // ---------- Binary operations ----------

    /**
     * Returns the concatenation of this [value] with the string representation
     * of the [other] object.
     */
    public infix operator fun plus(other: Any?): NotBlankStringJvm =
        NotBlankStringJvm(value + other)

    // ---------- Comparisons ----------

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    @SinceKotoolsTypes("2.0")
    override infix fun compareTo(other: String): Int = value.compareTo(other)

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: NotBlankStringJvm): Int =
        compareTo(other.value)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a non-zero int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents `0`.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNonZeroInt(): NonZeroIntJvm = value.toNonZeroIntJvm()

    /**
     * Returns this [value] as a non-zero int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents `0`.
     */
    @SinceKotoolsTypes("3.0")
    public fun toNonZeroIntOrNull(): NonZeroIntJvm? =
        value.toNonZeroIntJvmOrNull()

    /**
     * Returns this [value] as a positive int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly negative number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toPositiveInt(): PositiveIntJvm = value.toPositiveIntJvm()

    /**
     * Returns this [value] as a positive int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents a strictly
     * negative number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toPositiveIntOrNull(): PositiveIntJvm? =
        value.toPositiveIntJvmOrNull()

    /**
     * Returns this [value] as a strictly positive int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveIntJvm =
        value.toStrictlyPositiveIntJvm()

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] is not a valid representation of a number or if it
     * represents a negative number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveIntJvm? =
        value.toStrictlyPositiveIntJvmOrNull()

    /**
     * Returns this [value] as a negative int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a strictly positive number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toNegativeInt(): NegativeIntJvm = value.toNegativeIntJvm()

    /**
     * Returns this [value] as a negative int, or returns `null` if this [value]
     * is not a valid representation of a number or if it represents a strictly
     * positive number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toNegativeIntOrNull(): NegativeIntJvm? =
        value.toNegativeIntJvmOrNull()

    /**
     * Returns this [value] as a strictly negative int.
     * Throws a [NumberFormatException] if this [value] is not a valid
     * representation of a number, or throws an [IllegalArgumentException] if it
     * represents a positive number.
     */
    @SinceKotoolsTypes("3.0")
    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeIntJvm =
        value.toStrictlyNegativeIntJvm()

    /**
     * Returns this [value] as a strictly negative int, or returns `null` if
     * this [value] is not a valid representation of a number or if it
     * represents a positive number.
     */
    @SinceKotoolsTypes("3.0")
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeIntJvm? =
        value.toStrictlyNegativeIntJvmOrNull()

    override fun toString(): String = value

    public companion object {
        /**
         * Returns the [value] as a not blank string, or returns `null` if the
         * [value] is blank.
         */
        public infix fun orNull(value: String): NotBlankStringJvm? = try {
            NotBlankStringJvm(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<NotBlankStringJvm> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            NotBlankStringJvm::class.qualifiedName!!,
            PrimitiveKind.STRING
        )

        override fun serialize(
            encoder: Encoder,
            value: NotBlankStringJvm
        ): Unit = encoder.encodeString(value.value)

        override fun deserialize(decoder: Decoder): NotBlankStringJvm =
            decoder.decodeString().toNotBlankStringJvm()
    }
}
