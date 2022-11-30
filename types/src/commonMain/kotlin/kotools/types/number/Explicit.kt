package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Alpha
import kotools.types.toSuccessfulResult

/**
 * Representation of explicit numbers.
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface ExplicitNumber<out N : Number> {
    /** The value to hold. */
    public val value: N
}

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

/**
 * Representation of numbers other than zero.
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface NonZeroNumber<out N : Number> : ExplicitNumber<N>

private class NonZeroNumberImplementation<out N : Number>(
    override val value: N
) : NonZeroNumber<N>

/**
 * Representation of positive numbers (including zero).
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface PositiveNumber<out N : Number> : ExplicitNumber<N>

/**
 * Representation of negative numbers (including zero).
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface NegativeNumber<out N : Number> : ExplicitNumber<N>

/**
 * Representation of strictly positive numbers (excluding zero).
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface StrictlyPositiveNumber<out N : Number> : NonZeroNumber<N>,
    PositiveNumber<N>

/**
 * Representation of strictly negative numbers (excluding zero).
 *
 * @param N The type of [Number] to hold.
 */
@SinceKotools(Types, "3.2", Alpha)
public interface StrictlyNegativeNumber<out N : Number> : NonZeroNumber<N>,
    NegativeNumber<N>
