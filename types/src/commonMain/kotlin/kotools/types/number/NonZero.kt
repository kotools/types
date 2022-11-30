package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Alpha
import kotools.types.toSuccessfulResult

/**
 * Returns this number as a [NonZeroNumber], or an [IllegalArgumentException] if
 * this number equals zero.
 */
@SinceKotools(Types, "3.2", Alpha)
public fun <N : Number> N.toNonZeroNumber(): Result<NonZeroNumber<N>> =
    takeIf { it.toDouble() != 0.0 }
        ?.let(::NonZeroNumberImplementation)
        ?.toSuccessfulResult()
        ?: Result.failure(this shouldBe otherThanZero)

/** Multiplies this number by the [other] one. */
@SinceKotools(Types, "3.2", Alpha)
public operator fun NonZeroNumber<Int>.times(
    other: NonZeroNumber<Int>
): NonZeroNumber<Int> = (value * other.value)
    .toNonZeroNumber()
    .getOrThrow()

// (Byte | Short | Int).div(NonZeroNumber<Byte | Short | Int>): Int

/**
 * Divides this number by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", Alpha)
public operator fun Int.div(other: NonZeroNumber<Int>): Int = div(other.value)

// (Byte | Short | Int).div(NonZeroNumber<Long>): Long
// (Byte | Short | Int).div(NonZeroNumber<Float>): Float
// (Byte | Short | Int).div(NonZeroNumber<Double>): Double

// Long.div(NonZeroNumber<Byte | Short | Int | Long>): Long
// Long.div(NonZeroNumber<Float>): Float
// Long.div(NonZeroNumber<Double>): Double

// Float.div(NonZeroNumber<Byte | Short | Int | Long | Float>): Float
// Float.div(NonZeroNumber<Double>): Double

// <N : Number> Double.div(NonZeroNumber<N>): Double

/**
 * Representation of numbers other than zero.
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface NonZeroNumber<out N : Number> : ExplicitNumber<N>

private class NonZeroNumberImplementation<out N : Number>(
    override val value: N
) : ExplicitNumberImplementation<N>(),
    NonZeroNumber<N>
