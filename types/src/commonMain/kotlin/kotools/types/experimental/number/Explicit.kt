package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.experimental.ExperimentalKotoolsTypesApi

/**
 * Representation of explicit numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", StabilityLevel.Experimental)
public sealed interface ExplicitNumber<out N : Number> {
    /** The value to hold. */
    public val value: N
}
