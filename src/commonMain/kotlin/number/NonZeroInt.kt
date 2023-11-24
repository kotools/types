/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.range.NotEmptyRange
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/**
 * Returns this number as an encapsulated [NonZeroInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number equals [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toNonZeroInt(): Result<NonZeroInt> {
    val value: Int = toInt()
    return when {
        value.isStrictlyPositive() -> value.toStrictlyPositiveInt()
        value.isStrictlyNegative() -> value.toStrictlyNegativeInt()
        else -> Result.failure(NonZeroIntConstructionException)
    }
}

/** Representation of integers other than [zero][ZeroInt]. */
@Serializable(NonZeroIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public sealed interface NonZeroInt : AnyInt {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        /** The minimum value a [NonZeroInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            StrictlyNegativeInt.Companion::min
        )

        /** The maximum value a [NonZeroInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.Companion::max
        )

        /** The negative range of values a [NonZeroInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSince(KotoolsTypesVersion.V4_2_0)
        public val negativeRange: NotEmptyRange<StrictlyNegativeInt> by lazy(
            StrictlyNegativeInt.Companion::range
        )

        /** The positive range of values a [NonZeroInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSince(KotoolsTypesVersion.V4_2_0)
        public val positiveRange: NotEmptyRange<StrictlyPositiveInt> by lazy(
            StrictlyPositiveInt.Companion::range
        )

        /** Returns a random [NonZeroInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): NonZeroInt {
            val ranges: NotEmptySet<IntRange> = notEmptySetOf(
                min.toInt()..StrictlyNegativeInt.max.toInt(),
                StrictlyPositiveInt.min.toInt()..max.toInt()
            )
            return ranges.toSet()
                .random()
                .random()
                .toNonZeroInt()
                .getOrThrow()
        }
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String
}

/** Returns the negative of this integer. */
@ExperimentalNumberApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
public operator fun NonZeroInt.unaryMinus(): NonZeroInt = toInt()
    .unaryMinus()
    .toNonZeroInt()
    .getOrThrow()

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.div(other: NonZeroInt): Int = this / other.toInt()

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.rem(other: NonZeroInt): Int = this % other.toInt()

internal object NonZeroIntSerializer : AnyIntSerializer<NonZeroInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "${Package.NUMBER}.NonZeroInt".toNotBlankString()
    }

    override fun deserialize(value: Int): NonZeroInt = value.toNonZeroInt()
        .getOrNull()
        ?: throw NonZeroIntSerializationException
}

internal object NonZeroIntConstructionException : IllegalArgumentException() {
    override val message: String by lazy { "Number should be other than zero" }
}

private object NonZeroIntSerializationException : SerializationException() {
    override val message: String by lazy { "Number should be other than zero" }
}
