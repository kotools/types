@file:Suppress("DEPRECATION")

package kotools.types.string

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.strictlyPositiveIntOrThrow
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NotBlankString], or returns `null` if the [value]
 * is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "value.toNotBlankString().getOrNull()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun notBlankStringOrNull(value: String): NotBlankString? = value
    .takeIf(String::isNotBlank)
    ?.let(::NotBlankStringImplementation)

/**
 * Returns the [value] as a [NotBlankString], or throws an
 * [IllegalArgumentException] if the [value] is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "value.toNotBlankString().getOrThrow()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun notBlankStringOrThrow(value: String): NotBlankString =
    notBlankStringOrNull(value)
        ?: throw IllegalArgumentException("Given string shouldn't be blank.")

/**
 * Returns the [value] as a [NotBlankString], or throws an
 * [NotBlankString.ConstructionError] if the [value] is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "value.toNotBlankString().getOrThrow()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "1.2")
@Suppress("DEPRECATION")
@Throws(NotBlankString.ConstructionError::class)
public fun NotBlankString(value: String): NotBlankString =
    notBlankStringOrNull(value) ?: throw NotBlankString.ConstructionError()

/**
 * Returns the [value] as a [NotBlankString], or returns `null` if the [value]
 * is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "value.toNotBlankString().getOrNull()",
        "kotools.types.toNotBlankString"
    )
)
@Suppress("FunctionName")
@SinceKotools(Types, "3.0")
public fun NotBlankStringOrNull(value: String): NotBlankString? =
    notBlankStringOrNull(value)

/**
 * Returns this value as a [NotBlankString], or throws an
 * [NotBlankString.ConstructionError] if this value is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "this.toNotBlankString().getOrThrow()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "1.2")
@Suppress("DEPRECATION")
@Throws(NotBlankString.ConstructionError::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/**
 * Returns this value as a [NotBlankString], or returns `null` if this value is
 * blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "this.toNotBlankString().getOrNull()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    notBlankStringOrNull(this)

/**
 * Returns this value as a [NotBlankString], or throws an
 * [IllegalArgumentException] if this value is blank.
 */
@Deprecated(
    "Use the String.toNotBlankString function from the kotools.types package " +
            "instead.",
    ReplaceWith(
        "this.toNotBlankString().getOrThrow()",
        "kotools.types.toNotBlankString"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankStringOrThrow(): NotBlankString =
    notBlankStringOrThrow(this)

// ---------- Binary operations ----------

/**
 * Compares this value lexicographically with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@Deprecated(
    "Use the String.compareTo(NotBlankString) function from the " +
            "kotools.types package instead.",
    ReplaceWith("this.compareTo(other)", "kotools.types.compareTo")
)
@SinceKotools(Types, "2.0")
public infix operator fun String.compareTo(other: NotBlankString): Int =
    compareTo(other.value)

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@Deprecated(
    "Use the kotools.types.NotBlankString type instead.",
    ReplaceWith("NotBlankString", "kotools.types.NotBlankString")
)
@Serializable(NotBlankStringSerializer::class)
@SinceKotools(Types, "1.2")
public sealed interface NotBlankString : Comparable<NotBlankString> {
    /** The value to hold. */
    public val value: String

    // ---------- Query operations ----------

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = strictlyPositiveIntOrThrow(value.length)

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
    @SinceKotools(Types, "2.0")
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
                "${Package.string}.NotBlankStringOrNull"
            )
        )
        public infix fun orNull(value: String): NotBlankString? =
            notBlankStringOrNull(value)
    }

    /** Error thrown when creating a [NotBlankString] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
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

    override fun deserialize(decoder: Decoder): NotBlankString = delegate
        .deserialize(decoder)
        .toNotBlankStringOrThrow()
}
