/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.experimental.NotEmptyRange
import kotools.types.experimental.range
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.simpleNameOf

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

/** Represents an integer number of type [Int] that is other than zero. */
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
        @OptIn(ExperimentalKotoolsTypesApi::class)
        public val negativeRange: NotEmptyRange<StrictlyNegativeInt> by lazy(
            StrictlyNegativeInt.Companion::range
        )

        /** The positive range of values a [NonZeroInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSince(KotoolsTypesVersion.V4_2_0)
        @OptIn(ExperimentalKotoolsTypesApi::class)
        public val positiveRange: NotEmptyRange<StrictlyPositiveInt> by lazy {
            StrictlyPositiveInt.range
        }

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

/**
 * Represents a serializer of [NonZeroInt] that is responsible for serializing
 * it as [Int] and for deserializing it from an [Int].
 *
 * The serialization process is delegated to the serializer returned by the
 * [intSerializer] function.
 *
 * See the [NonZeroIntDeserializationStrategy] type for more details on this
 * serializer's descriptor and the deserialization process of [NonZeroInt]
 * regarding this serializer.
 */
private object NonZeroIntSerializer : KSerializer<NonZeroInt> by intSerializer(
    NonZeroIntDeserializationStrategy,
    intConverter = { it.toInt() }
)

/**
 * Represents a deserialization strategy for deserializing a [NonZeroInt] from
 * an [Int].
 *
 * See the [NonZeroIntDeserializationStrategy.descriptor] property for more
 * details on the structure of the serializable representation of [NonZeroInt].
 *
 * See the [NonZeroIntDeserializationStrategy.deserialize] function for more
 * details on the deserialization process.
 */
private object NonZeroIntDeserializationStrategy :
    DeserializationStrategy<NonZeroInt> {
    /**
     * Describes the structure of the serializable representation of
     * [NonZeroInt].
     *
     * More precisely, this is a primitive serial descriptor of kind
     * [PrimitiveKind.INT] having a [serial name][SerialDescriptor.serialName]
     * that matches the [qualified name][kotlin.reflect.KClass.qualifiedName]
     * of [NonZeroInt].
     */
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<NonZeroInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    /**
     * Deserializes the [NonZeroInt] value from an [Int], using the format
     * represented by the specified [decoder], or throws a
     * [NonZeroIntSerializationException] if the decoded [Int] equals zero.
     */
    override fun deserialize(decoder: Decoder): NonZeroInt {
        val value: Int = decoder.decodeInt()
        return value.toNonZeroInt()
            .getOrNull()
            ?: throw NonZeroIntSerializationException
    }
}

internal object NonZeroIntConstructionException : IllegalArgumentException() {
    override val message: String by lazy { "Number should be other than zero" }
}

private object NonZeroIntSerializationException : SerializationException() {
    override val message: String by lazy { "Number should be other than zero" }
}
