package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.NUMBER_PACKAGE
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.NotEmptyRange
import kotools.types.range.notEmptyRangeOf
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmSynthetic

/**
 * Returns this number as an encapsulated [PositiveInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toPositiveInt(): Result<PositiveInt> = toInt().runCatching {
    require(this >= 0) { shouldBePositiveMessage() }
    if (this == 0) ZeroInt else toStrictlyPositiveInt().getOrThrow()
}

/**
 * Returns this number as a [PositiveInt], which may involve rounding or
 * truncation, or returns `null` if this number is
 * [strictly negative][StrictlyNegativeInt].
 *
 * ```kotlin
 * var result: PositiveInt? = 1.toPositiveIntOrNull()
 * println(result) // 1
 *
 * result = 0.toPositiveIntOrNull()
 * println(result) // 0
 *
 * result = (-1).toPositiveIntOrNull()
 * println(result) // null
 * ```
 *
 * You can use the [toPositiveIntOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null` when this number is
 * [strictly negative][StrictlyNegativeInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toPositiveIntOrNull(): PositiveInt? {
    val value: Int = toInt()
    return when {
        value == 0 -> ZeroInt
        value.isStrictlyPositive() -> StrictlyPositiveInt(value)
        else -> null
    }
}

/**
 * Returns this number as a [PositiveInt], which may involve rounding or
 * truncation, or throws [IllegalArgumentException] if this number is
 * [strictly negative][StrictlyNegativeInt].
 *
 * ```kotlin
 * var result: PositiveInt = 1.toPositiveIntOrThrow()
 * println(result) // 1
 *
 * result = 0.toPositiveIntOrThrow()
 * println(result) // 0
 *
 * (-1).toPositiveIntOrThrow() // IllegalArgumentException
 * ```
 *
 * You can use the [toPositiveIntOrNull] function for returning `null` instead
 * of throwing an [IllegalArgumentException] when this number is
 * [strictly negative][StrictlyNegativeInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toPositiveIntOrThrow(): PositiveInt {
    val value: Int = toInt()
    require(value >= 0) { value.shouldBePositiveMessage() }
    return if (value == 0) ZeroInt else value.toStrictlyPositiveIntOrThrow()
}

/** Representation of positive integers including [zero][ZeroInt]. */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy { StrictlyPositiveInt.max }

        /** The range of values a [PositiveInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<PositiveInt> by lazy {
            val end: StrictlyPositiveInt = StrictlyPositiveInt.range.end.value
            notEmptyRangeOf { ZeroInt.inclusive to end.inclusive }
        }

        /** Returns a random [PositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt = (min.toInt()..max.toInt())
            .random()
            .toPositiveInt()
            .getOrThrow()
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int

    @SinceKotoolsTypes("4.0")
    override fun toString(): String
}

/** Returns the negative of this integer. */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
public operator fun PositiveInt.unaryMinus(): NegativeInt = toInt()
    .unaryMinus()
    .toNegativeIntOrThrow()

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

internal object PositiveIntSerializer : AnyIntSerializer<PositiveInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "$NUMBER_PACKAGE.PositiveInt".toNotBlankString()
    }

    override fun deserialize(value: Int): PositiveInt = value.toPositiveInt()
        .getOrNull()
        ?: value.let {
            val message: String = it.shouldBePositiveMessage()
            throw SerializationException(message)
        }
}

@JvmSynthetic
internal fun Number.shouldBePositiveMessage(): String =
    "Number should be positive (tried with $this)."
