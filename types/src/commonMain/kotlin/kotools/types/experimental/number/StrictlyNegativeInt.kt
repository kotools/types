package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmInline

/** Representation of strictly negative integers, excluding zero. */
@ExperimentalKotoolsTypesApi
@JvmInline
@SinceKotools(Types, "1.1", Experimental)
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
