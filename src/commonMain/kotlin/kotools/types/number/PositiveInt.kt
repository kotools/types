package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.range.toInclusiveBound
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of positive integers including [zero][ZeroInt]. */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("PositiveInt.range.start.value")
        )
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("PositiveInt.range.end.value")
        )
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.range.end::value
        )

        /** The range of values a [PositiveInt] can have. */
        @SinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<PositiveInt> by lazy {
            val start: InclusiveBound<PositiveInt> = ZeroInt.toInclusiveBound()
            val end: InclusiveBound<PositiveInt> =
                StrictlyPositiveInt.range.end.value.toInclusiveBound()
            start..end
        }

        /** Returns a random [PositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt = range.toIntRange()
            .random()
            .toPositiveInt()
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
public operator fun PositiveInt.div(other: StrictlyPositiveInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.1")
public operator fun PositiveInt.div(other: StrictlyNegativeInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@SinceKotoolsTypes("4.1")
public operator fun PositiveInt.rem(other: NonZeroInt): PositiveInt {
    val result: Int = toInt() % other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Returns this number as an encapsulated [PositiveInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toPositiveInt(): Result<PositiveInt> {
    val value: Int = toInt()
    return when {
        value == ZeroInt.toInt() -> Result.success(ZeroInt)
        value > ZeroInt.toInt() -> value.toStrictlyPositiveInt()
        else -> Result.failure(value shouldBe aPositiveNumber)
    }
}

internal object PositiveIntSerializer : AnyIntSerializer<PositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.PositiveInt"::toNotBlankString
    )

    override fun deserialize(value: Int): PositiveInt = value.toPositiveInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aPositiveNumber)
}
