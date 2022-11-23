package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.collections.NotEmptySet
import kotools.types.collections.notEmptySetOf
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Types, "1.1", Experimental)
public value class NonZeroInt private constructor(override val value: Int) :
    NonZeroNumber<Int> {
    /** Returns a string representation of this [value]. */
    override fun toString(): String = value.toString()

    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        internal infix fun of(value: Int): Result<NonZeroInt> = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.toSuccessfulResult()
            ?: Result.failure(NonZeroNumber.Exception)
    }
}

@ExperimentalKotoolsTypesApi
internal val NonZeroInt.Companion.negativeRange: IntRange by lazy {
    Int.MIN_VALUE..-1
}

@ExperimentalKotoolsTypesApi
internal val NonZeroInt.Companion.positiveRange: IntRange by lazy {
    1..Int.MAX_VALUE
}

@ExperimentalKotoolsTypesApi
internal val NonZeroInt.Companion.ranges: NotEmptySet<IntRange> by lazy {
    notEmptySetOf(NonZeroInt.negativeRange, NonZeroInt.positiveRange)
}

/** The minimum value of a [NonZeroInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public val NonZeroInt.Companion.min: NonZeroInt by lazy {
    NonZeroInt.negativeRange.first.nonZero.getOrThrow()
}

/** The maximum value of a [NonZeroInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public val NonZeroInt.Companion.max: NonZeroInt by lazy {
    NonZeroInt.positiveRange.last.nonZero.getOrThrow()
}

/**
 * Returns this value as a [NonZeroInt], or a [NonZeroNumber.Exception] if this
 * value equals zero.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public val Int.nonZero: Result<NonZeroInt> get() = NonZeroInt of this

/** Returns a random [NonZeroInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.0", Experimental)
public fun NonZeroInt.Companion.random(): NonZeroInt = ranges.random()
    .random()
    .nonZero.getOrThrow()

/**
 * Returns this value incremented by one, or `1` if this integer equals `-1`, or
 * [NonZeroInt.Companion.min] if this value equals [NonZeroInt.Companion.max].
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public operator fun NonZeroInt.inc(): NonZeroInt = when (value) {
    -1 -> 1.nonZero.getOrThrow()
    NonZeroInt.max.value -> NonZeroInt.min
    else -> (value + 1).nonZero.getOrThrow()
}

/**
 * Returns this value decremented by one, or `-1` if this integer equals `1`, or
 * [NonZeroInt.Companion.max] if this value equals [NonZeroInt.Companion.min].
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public operator fun NonZeroInt.dec(): NonZeroInt = when (value) {
    1 -> (-1).nonZero.getOrThrow()
    NonZeroInt.min.value -> NonZeroInt.max
    else -> (value - 1).nonZero.getOrThrow()
}

/** Returns the negative of this value. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "1.1", Experimental)
public operator fun NonZeroInt.unaryMinus(): NonZeroInt =
    (-value).nonZero.getOrThrow()
