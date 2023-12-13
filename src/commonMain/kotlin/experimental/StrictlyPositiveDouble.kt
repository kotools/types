/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.hashCodeOf
import kotlin.jvm.JvmSynthetic

private fun Double.isStrictlyPositive(): Boolean = this > 0.0

/**
 * Returns this number as an encapsulated [StrictlyPositiveDouble],
 * which may involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is negative.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_2)
public fun Number.toStrictlyPositiveDouble(): Result<StrictlyPositiveDouble> =
    runCatching {
        val value: Double = toDouble()
        StrictlyPositiveDouble(value)
    }

/**
 * Represents a floating-point number of type [Double] that is greater than
 * zero.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_2)
@Serializable(StrictlyPositiveDoubleSerializer::class)
public class StrictlyPositiveDouble internal constructor(
    private val value: Double
) : Comparable<StrictlyPositiveDouble> {
    init {
        val isValid: Boolean = value.isStrictlyPositive()
        require(isValid) { StrictlyPositiveDoubleException(value).message }
    }

    /**
     * Returns `true` if the [other] object is an instance of
     * [StrictlyPositiveDouble] with a value that equals this instance's value,
     * or returns `false` otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is StrictlyPositiveDouble && other.value == value

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

    /** Returns a hash code value for this floating-point number. */
    override fun hashCode(): Int = hashCodeOf(value)

    /** Returns this floating-point number as a [Double]. */
    public fun toDouble(): Double = value

    /** Returns the string representation of this floating-point number. */
    override fun toString(): String = "$value"

    /** Contains static declarations for the [StrictlyPositiveDouble] type. */
    public companion object {
        @JvmSynthetic
        internal fun creationErrorMessageWith(number: Number): String =
            "Number should be strictly positive (tried with $number)."
    }
}

@ExperimentalKotoolsTypesApi
private object StrictlyPositiveDoubleSerializer :
    KSerializer<StrictlyPositiveDouble> {
    override val descriptor: SerialDescriptor by lazy {
        PrimitiveSerialDescriptor(
            serialName = "${Package.NUMBER}.StrictlyPositiveDouble",
            PrimitiveKind.DOUBLE
        )
    }

    override fun serialize(encoder: Encoder, value: StrictlyPositiveDouble) {
        val x: Double = value.toDouble()
        encoder.encodeDouble(x)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveDouble {
        val value: Double = decoder.decodeDouble()
        return value.toStrictlyPositiveDouble()
            .getOrNull()
            ?: throw StrictlyPositiveDoubleSerializationException(value)
    }
}

private class StrictlyPositiveDoubleException(number: Number) :
    IllegalArgumentException() {
    @ExperimentalKotoolsTypesApi
    override val message: String by lazy {
        StrictlyPositiveDouble.creationErrorMessageWith(number)
    }
}

private class StrictlyPositiveDoubleSerializationException(number: Number) :
    SerializationException() {
    @ExperimentalKotoolsTypesApi
    override val message: String by lazy {
        StrictlyPositiveDouble.creationErrorMessageWith(number)
    }
}
