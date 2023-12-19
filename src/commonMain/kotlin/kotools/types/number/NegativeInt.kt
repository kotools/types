/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.experimental.NotEmptyRange
import kotools.types.experimental.notEmptyRangeOf
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

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
            val exception = NegativeIntConstructionException(value)
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

        /** The range of values a [NegativeInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSince(KotoolsTypesVersion.V4_2_0)
        @OptIn(ExperimentalKotoolsTypesApi::class)
        public val range: NotEmptyRange<NegativeInt> by lazy {
            val start: StrictlyNegativeInt =
                StrictlyNegativeInt.range.start.value
            notEmptyRangeOf { start.inclusive to ZeroInt.inclusive }
        }

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

internal object NegativeIntSerializer :
    AnyIntSerializerDeprecated<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "${KotoolsTypesPackage.Number}.NegativeInt".toNotBlankString()
    }

    override fun deserialize(value: Int): NegativeInt = value.toNegativeInt()
        .getOrNull()
        ?: throw NegativeIntSerializationException(value)
}

internal class NegativeIntConstructionException(number: Number) :
    IllegalArgumentException() {
    override val message: String by lazy {
        "Number should be negative (tried with $number)."
    }
}

private class NegativeIntSerializationException(number: Number) :
    SerializationException() {
    override val message: String by lazy {
        "Number should be negative (tried with $number)."
    }
}
