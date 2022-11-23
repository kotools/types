package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of positive integers, including zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Types, "1.1", Experimental)
public value class PositiveInt private constructor(override val value: Int) :
    PositiveNumber<Int> {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        internal infix fun of(value: Int): Result<PositiveInt> = value
            .takeIf { it >= 0 }
            ?.let(::PositiveInt)
            ?.toSuccessfulResult()
            ?: Result.failure(PositiveNumber.Exception(value))
    }
}

@ExperimentalKotoolsTypesApi
internal val PositiveInt.Companion.range: IntRange by lazy { 0..Int.MAX_VALUE }

/**
 * Returns this value as a [PositiveInt], or a [PositiveNumber.Exception] if
 * this value is strictly negative.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public val Int.positive: Result<PositiveInt> get() = PositiveInt of this
