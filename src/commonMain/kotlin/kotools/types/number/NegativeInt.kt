/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBeNegative
import kotools.types.internal.simpleNameOf

/**
 * Returns this number as an encapsulated [NegativeInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly positive][StrictlyPositiveInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toNegativeInt(): Result<NegativeInt> {
    val value: Int = toInt()
    return when {
        value == 0 -> Result.success(ZeroInt)
        value.isStrictlyNegative() -> value.toStrictlyNegativeInt()
        else -> {
            val message: ErrorMessage = value.shouldBeNegative()
            val exception = IllegalArgumentException("$message")
            Result.failure(exception)
        }
    }
}

/**
 * Represents an integer number of type [Int] that is less than or equals zero.
 */
@Serializable(NegativeIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy { StrictlyNegativeInt.min }

        /** The maximum value a [NegativeInt] can have. */
        public val max: ZeroInt = ZeroInt

        /** Returns a random [NegativeInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): NegativeInt = (min.toInt()..max.toInt())
            .random()
            .toNegativeInt()
            .getOrThrow()
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun NegativeInt.div(other: StrictlyPositiveInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun NegativeInt.div(other: StrictlyNegativeInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun NegativeInt.rem(other: NonZeroInt): NegativeInt {
    val result: Int = toInt() % other
    return result.toNegativeInt()
        .getOrThrow()
}

private object NegativeIntSerializer :
    KSerializer<NegativeInt> by intSerializer(
        NegativeIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object NegativeIntDeserializationStrategy :
    DeserializationStrategy<NegativeInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<NegativeInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): NegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toNegativeInt()
            .getOrNull()
            ?: serializationError(value.shouldBeNegative())
    }
}
