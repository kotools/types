package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.NotEmptyRange
import kotools.types.range.notEmptyRangeOf
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/**
 * Returns this number as an encapsulated [NegativeInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("4.1")
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
 * Returns this number as a [NegativeInt], which may involve rounding or
 * truncation, or returns `null` if this number is
 * [strictly positive][StrictlyPositiveInt].
 *
 * ```kotlin
 * var result: NegativeInt? = (-1).toNegativeIntOrNull()
 * println(result) // -1
 *
 * result = 0.toNegativeIntOrNull()
 * println(result) // 0
 *
 * result = 1.toNegativeIntOrNull()
 * println(result) // null
 * ```
 *
 * You can use the [toNegativeIntOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null` when this number is
 * [strictly positive][StrictlyPositiveInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toNegativeIntOrNull(): NegativeInt? = NegativeInt.of(this)

/**
 * Returns this number as a [NegativeInt], which may involve rounding or
 * truncation, or throws [IllegalArgumentException] if this number is
 * [strictly positive][StrictlyPositiveInt].
 *
 * ```kotlin
 * var result: NegativeInt = (-1).toNegativeIntOrThrow()
 * println(result) // -1
 *
 * result = 0.toNegativeIntOrThrow()
 * println(result) // 0
 *
 * 1.toNegativeIntOrThrow() // IllegalArgumentException
 * ```
 *
 * You can use the [toNegativeIntOrNull] function for returning `null` instead
 * of throwing an [IllegalArgumentException] when this number is
 * [strictly positive][StrictlyPositiveInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toNegativeIntOrThrow(): NegativeInt {
    val value: NegativeInt? = NegativeInt.of(this)
    return requireNotNull(value) {
        NegativeIntConstructionException(this).message
    }
}

/** Representation of negative integers including [zero][ZeroInt]. */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy { StrictlyNegativeInt.min }

        /** The maximum value a [NegativeInt] can have. */
        public val max: ZeroInt = ZeroInt

        /** The range of values a [NegativeInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<NegativeInt> by lazy {
            val start: StrictlyNegativeInt =
                StrictlyNegativeInt.range.start.value
            notEmptyRangeOf { start.inclusive to ZeroInt.inclusive }
        }

        /**
         * Returns the given [number] as a [NegativeInt], which may involve
         * rounding or truncation, or returns `null` if the [number] is
         * [strictly positive][StrictlyPositiveInt].
         *
         * ```kotlin
         * var result: NegativeInt? = NegativeInt.of(-1)
         * println(result) // -1
         *
         * result = NegativeInt.of(0)
         * println(result) // 0
         *
         * result = NegativeInt.of(1)
         * println(result) // null
         * ```
         */
        @ExperimentalNumberApi
        @ExperimentalSinceKotoolsTypes("4.3.2")
        public fun of(number: Number): NegativeInt? {
            val value: Int = number.toInt()
            return if (value > ZeroInt) null
            else StrictlyNegativeInt.of(value) ?: ZeroInt
        }

        /** Returns a random [NegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): NegativeInt = (min.toInt()..max.toInt())
            .random()
            .toNegativeInt()
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
public operator fun NegativeInt.unaryMinus(): PositiveInt = toInt()
    .unaryMinus()
    .toPositiveIntOrThrow()

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

internal object NegativeIntSerializer : AnyIntSerializer<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "${Package.number}.NegativeInt".toNotBlankString()
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
