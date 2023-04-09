package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.range.rangeTo
import kotools.types.range.toInclusiveBound
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of negative integers including [zero][ZeroInt]. */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("NegativeInt.range.start.value")
        )
        public val min: StrictlyNegativeInt by lazy(
            StrictlyNegativeInt.range.start::value
        )

        /** The maximum value a [NegativeInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("NegativeInt.range.end.value")
        )
        public val max: ZeroInt = ZeroInt

        /** The range of values a [NegativeInt] can have. */
        @SinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<NegativeInt> by lazy {
            val start: InclusiveBound<NegativeInt> =
                StrictlyNegativeInt.range.start.value.toInclusiveBound()
            val end: InclusiveBound<NegativeInt> = ZeroInt.toInclusiveBound()
            start..end
        }

        /** Returns a random [NegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): NegativeInt = range.toIntRange()
            .random()
            .toNegativeInt()
            .getOrThrow()
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int

    @SinceKotoolsTypes("4.0")
    override fun toString(): String
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.1")
public operator fun NegativeInt.div(other: StrictlyPositiveInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.1")
public operator fun NegativeInt.div(other: StrictlyNegativeInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@SinceKotoolsTypes("4.1")
public operator fun NegativeInt.rem(other: NonZeroInt): NegativeInt {
    val result: Int = toInt() % other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Returns this number as an encapsulated [NegativeInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toNegativeInt(): Result<NegativeInt> {
    val value: Int = toInt()
    return when {
        value == ZeroInt.toInt() -> Result.success(ZeroInt)
        value < ZeroInt.toInt() -> value.toStrictlyNegativeInt()
        else -> Result.failure(value shouldBe aNegativeNumber)
    }
}

internal object NegativeIntSerializer : AnyIntSerializer<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): NegativeInt = value.toNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aNegativeNumber)
}
