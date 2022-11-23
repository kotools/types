package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.collections.NotEmptySet
import kotools.types.collections.notEmptySetOf
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of numbers other than zero.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public interface NonZeroNumber<out N : Number> : ExplicitNumber<N> {
    public object Exception :
        IllegalArgumentException("Given value shouldn't equal zero.")
}

/**
 * Returns this integer as a [NonZeroInt], or returns a
 * [NonZeroNumber.Exception] if this integer equals zero.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public val Int.nonZero: Result<NonZeroInt> get() = NonZeroInt(this)

/**
 * Increment this integer by one, or returns `1` when this integer equals `-1`,
 * or returns [NonZeroInt.min] when this integer equals [NonZeroInt.max].
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public operator fun NonZeroInt.inc(): NonZeroInt = when (value) {
    -1 -> 1.nonZero.getOrThrow()
    NonZeroInt.max.value -> NonZeroInt.min
    else -> (value + 1).nonZero.getOrThrow()
}

/** Representation of integers other than zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Types, "1.1", Experimental)
public value class NonZeroInt private constructor(override val value: Int) :
    NonZeroNumber<Int> {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        private val negativeRange: IntRange by lazy { Int.MIN_VALUE..-1 }
        private val positiveRange: IntRange by lazy { 1..Int.MAX_VALUE }
        private val ranges: NotEmptySet<IntRange> by lazy {
            notEmptySetOf(negativeRange, positiveRange)
        }

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt by lazy {
            negativeRange.first.nonZero.getOrThrow()
        }

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt by lazy {
            positiveRange.last.nonZero.getOrThrow()
        }

        internal operator fun invoke(value: Int): Result<NonZeroInt> = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.toSuccessfulResult()
            ?: Result.failure(NonZeroNumber.Exception)

        /** Returns a random [NonZeroInt]. */
        @SinceKotools(Types, "3.0", Experimental)
        public fun random(): NonZeroInt = ranges.random()
            .random()
            .nonZero.getOrThrow()
    }
}
