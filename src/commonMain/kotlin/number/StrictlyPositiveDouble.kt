/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.deserializationErrorMessage
import kotools.types.internal.hashCodeOf
import kotools.types.internal.serialNameOf

/**
 * Represents a floating-point number of type [Double] that is greater than
 * zero.
 *
 * You can use the [StrictlyPositiveDouble.Companion.orNull] function for
 * creating an instance of this type.
 *
 * The serialization of this type is similar to the serialization of [Double].
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * ```kotlin
 * val value = 2.0
 * val number: StrictlyPositiveDouble = StrictlyPositiveDouble.orNull(value)
 *     ?: error("Creating a StrictlyPositiveDouble should pass with $value")
 * val encoded: String = Json.encodeToString(number)
 * println(encoded) // 2.0
 * val decoded: StrictlyPositiveDouble = Json.decodeFromString(encoded)
 * println(number == decoded) // true
 * ```
 */
@ExperimentalNumberApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@Serializable(StrictlyPositiveDoubleSerializer::class)
public class StrictlyPositiveDouble private constructor(
    private val value: Double
) : Comparable<StrictlyPositiveDouble> {
    init {
        require(value > 0) {
            "Number should be greater than zero (tried with $value)"
        }
    }

    /**
     * Returns `true` if the [other] object points to the same reference as this
     * floating-point number, or if the [other] object is an instance of
     * [StrictlyPositiveDouble] having the same value as this floating-point
     * number.
     * Returns `false` otherwise.
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
    override fun equals(other: Any?): Boolean =
        if (this === other) true
        else other is StrictlyPositiveDouble && this.value == other.value

    /**
     * Compares this floating-point number with the [other] one for order.
     * Returns zero if this floating-point number equals the [other] one, a
     * strictly negative number if it's less than the [other] one, or a strictly
     * positive number if it's greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int =
        value.compareTo(other.value)

    /** Returns a hash code for this floating-point number. */
    @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
    override fun hashCode(): Int = hashCodeOf(value)

    /** Returns this floating-point number as [Double]. */
    public fun toDouble(): Double = value

    /** Returns this floating-point number as [String]. */
    override fun toString(): String = "$value"

    /** Contains static declarations for the [StrictlyPositiveDouble] type. */
    public companion object {
        /**
         * Creates an instance of [StrictlyPositiveDouble] with the specified
         * [value], or returns `null` if the [value] is less than or equals
         * zero.
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
        public fun orNull(value: Double): StrictlyPositiveDouble? =
            if (value <= 0) null
            else StrictlyPositiveDouble(value)
    }
}

@ExperimentalNumberApi
private object StrictlyPositiveDoubleSerializer :
    KSerializer<StrictlyPositiveDouble> {
    override val descriptor: SerialDescriptor by lazy {
        val serialName: String = serialNameOf<StrictlyPositiveDouble>()
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.DOUBLE)
    }

    override fun serialize(encoder: Encoder, value: StrictlyPositiveDouble) {
        val convertedValue: Double = value.toDouble()
        encoder.encodeDouble(convertedValue)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveDouble {
        val value: Double = decoder.decodeDouble()
        return StrictlyPositiveDouble.orNull(value) ?: run {
            val message: String =
                deserializationErrorMessage<StrictlyPositiveDouble>(value)
            throw SerializationException(message)
        }
    }
}
