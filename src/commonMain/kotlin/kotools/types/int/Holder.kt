package kotools.types.int

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.Holder
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.Validator

internal fun IntHolder(
    value: Int,
    validator: Validator<Int>? = null
): IntHolder = IntHolderImplementation(value, validator)

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.minus(other: IntHolder): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.times(other: IntHolder): Int = times(other.value)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.div(other: IntHolder): Int = div(other.value)

// ---------- Comparisons ----------

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Holder<Int>, Comparable<IntHolder> {
    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: IntHolder): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: IntHolder): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: IntHolder): Int = times(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: IntHolder): Int = div(other.value)

    // ---------- Comparisons ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: IntHolder): Int = compareTo(other.value)
}

private class IntHolderImplementation(
    override val value: Int,
    validator: Validator<Int>? = null
) : IntHolder,
    Holder<Int> by Holder(value) {
    init {
        validator?.let { require(it isValid value) }
    }

    override fun equals(other: Any?): Boolean =
        other is IntHolder && value == other.value

    override fun hashCode(): Int = value.hashCode()
}

@Suppress("FunctionName")
internal fun <T : IntHolder> IntHolderSerializer(
    serialName: String = "IntHolder",
    builder: (Int) -> T
): IntHolderSerializer<T> =
    IntHolderSerializerImplementation(serialName, builder)

internal sealed interface IntHolderSerializer<T : IntHolder> : KSerializer<T>

private class IntHolderSerializerImplementation<T : IntHolder>(
    private val serialName: String,
    private val builder: (Int) -> T
) : IntHolderSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: T): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): T {
        val value: Int = decoder.decodeInt()
        return builder(value)
    }
}
