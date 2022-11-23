package kotools.types.experimental.number

import kotools.shared.Project
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmInline

// ---------- Number ----------

/**
 * Representation of strictly negative numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Project.Types, "3.2", StabilityLevel.Experimental)
public interface StrictlyNegativeNumber<out N : Number> : NonZeroNumber<N>,
    NegativeNumber<N>

// ---------- Int ----------

/** Representation of strictly negative integers, excluding zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Project.Types, "1.1", StabilityLevel.Experimental)
public value class StrictlyNegativeInt
private constructor(override val value: Int) : StrictlyNegativeNumber<Int> {
    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object
}

@ExperimentalKotoolsTypesApi
internal val StrictlyNegativeInt.Companion.range: IntRange by lazy {
    Int.MIN_VALUE..-1
}
