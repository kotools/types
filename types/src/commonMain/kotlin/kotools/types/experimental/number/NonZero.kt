package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.experimental.ExperimentalKotoolsTypesApi

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
