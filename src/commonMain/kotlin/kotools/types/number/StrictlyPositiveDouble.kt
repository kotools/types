package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.NUMBER_PACKAGE
import kotools.types.experimental.ExperimentalNumberApi
import kotlin.jvm.JvmInline

private fun Double.isStrictlyPositive(): Boolean = this > 0.0

/**
 * Returns this number as an encapsulated [StrictlyPositiveDouble],
 * which may involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is negative.
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
public fun Number.toStrictlyPositiveDouble(): Result<StrictlyPositiveDouble> =
    runCatching {
        val value: Double = toDouble()
        StrictlyPositiveDouble(value)
    }

/**
 * Returns this number as a [StrictlyPositiveDouble], which may involve rounding
 * or truncation, or returns `null` if this number is negative.
 *
 * ```kotlin
 * var result: StrictlyPositiveDouble? = 1.toStrictlyPositiveDoubleOrNull()
 * println(result) // 1.0
 *
 * result = 0.toStrictlyPositiveDoubleOrNull()
 * println(result) // null
 *
 * result = (-1).toStrictlyPositiveDoubleOrNull()
 * println(result) // null
 * ```
 *
 * You can use the [toStrictlyPositiveDoubleOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null`.
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toStrictlyPositiveDoubleOrNull(): StrictlyPositiveDouble? {
    val value: Double = toDouble()
    val isValid: Boolean = value.isStrictlyPositive()
    return if (isValid) StrictlyPositiveDouble(value) else null
}

/**
 * Returns this number as a [StrictlyPositiveDouble], which may involve rounding
 * or truncation, or throws [IllegalArgumentException] if this number is
 * negative.
 *
 * ```kotlin
 * val result: StrictlyPositiveDouble = 1.toStrictlyPositiveDoubleOrThrow()
 * println(result) // 1.0
 *
 * 0.toStrictlyPositiveDoubleOrThrow() // IllegalArgumentException
 * (-1).toStrictlyPositiveDoubleOrThrow() // IllegalArgumentException
 * ```
 *
 * You can use the [toStrictlyPositiveDoubleOrNull] function for returning
 * `null` instead of throwing an [IllegalArgumentException] when this number is
 * negative.
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toStrictlyPositiveDoubleOrThrow(): StrictlyPositiveDouble {
    val value: Double = toDouble()
    return StrictlyPositiveDouble(value)
}

/**
 * Represents strictly positive floating-point numbers represented by the
 * [Double] type.
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
@JvmInline
@Serializable(StrictlyPositiveDoubleSerializer::class)
public value class StrictlyPositiveDouble internal constructor(
    private val value: Double
) : Comparable<StrictlyPositiveDouble> {
    init {
        val isValid: Boolean = value.isStrictlyPositive()
        require(isValid) { StrictlyPositiveDoubleException(value).message }
    }

    /**
     * Compares this floating-point number with the other one for order.
     * Returns zero if this floating-point number equals the other one,
     * a negative number if it's less than the other one,
     * or a positive number if it's greater than the other one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int {
        val x: Double = toDouble()
        val y: Double = other.toDouble()
        return x.compareTo(y)
    }

    /** Returns this floating-point number as a [Double]. */
    public fun toDouble(): Double = value

    /** Returns the string representation of this floating-point number. */
    override fun toString(): String = "$value"
}

@ExperimentalNumberApi
internal object StrictlyPositiveDoubleSerializer :
    KSerializer<StrictlyPositiveDouble> {
    override val descriptor: SerialDescriptor by lazy {
        PrimitiveSerialDescriptor(
            serialName = "$NUMBER_PACKAGE.StrictlyPositiveDouble",
            kind = PrimitiveKind.DOUBLE
        )
    }

    override fun serialize(encoder: Encoder, value: StrictlyPositiveDouble) {
        val x: Double = value.toDouble()
        encoder.encodeDouble(x)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveDouble {
        val value: Double = decoder.decodeDouble()
        return value.toStrictlyPositiveDoubleOrNull()
            ?: throw StrictlyPositiveDoubleSerializationException(value)
    }
}

internal class StrictlyPositiveDoubleException(number: Number) :
    IllegalArgumentException() {
    override val message: String by lazy {
        "Number should be strictly positive (tried with $number)."
    }
}

private class StrictlyPositiveDoubleSerializationException(number: Number) :
    SerializationException() {
    override val message: String by lazy {
        "Number should be strictly positive (tried with $number)."
    }
}
