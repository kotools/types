package kotools.types.experimental.number

import kotools.shared.Project
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

// ---------- Number ----------

/**
 * Representation of positive numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Project.Types, "3.2", StabilityLevel.Experimental)
public interface PositiveNumber<out N : Number> : ExplicitNumber<N> {
    /** Exception returned when building a [PositiveNumber] fails. */
    public class Exception(value: Int) : IllegalArgumentException(
        "Given value should be positive (tried with $value)."
    )
}

// ---------- Int ----------

/** Representation of positive integers, including zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Project.Types, "1.1", StabilityLevel.Experimental)
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
@SinceKotools(Project.Types, "3.2", StabilityLevel.Experimental)
public val Int.positive: Result<PositiveInt> get() = PositiveInt of this
