package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.failureOf
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotools.types.toSuccessfulResult

/**
 * Returns this number as a [NonZeroNumber], or an [IllegalArgumentException] if
 * this number equals zero.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public fun <N : Number> N.toNonZeroNumber(): Result<NonZeroNumber<N>> =
    takeIf { it.toDouble() != 0.0 }
        ?.toSuccessfulResult(::NonZeroNumberImplementation)
        ?: failureOf { this shouldBe otherThanZero }

// (Byte | Short | Int).div(NonZeroNumber<Byte | Short | Int>): Int
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
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroNumber<out N : Number> : ExplicitNumber<N>

@ExperimentalKotoolsTypesApi
private class NonZeroNumberImplementation<out N : Number>(
    override val value: N
) : ExplicitNumberImplementation<N>(),
    NonZeroNumber<N>
