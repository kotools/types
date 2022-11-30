package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/**
 * Representation of explicit numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitNumber<out N : Number> {
    /** The value to hold. */
    public val value: N
}

/**
 * Representation of positive numbers (including zero).
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveNumber<out N : Number> : ExplicitNumber<N>

/**
 * Representation of negative numbers (including zero).
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeNumber<out N : Number> : ExplicitNumber<N>

/**
 * Representation of strictly positive numbers (excluding zero).
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveNumber<out N : Number> :
    NonZeroNumber<N>,
    PositiveNumber<N>

/**
 * Representation of strictly negative numbers (excluding zero).
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeNumber<out N : Number> :
    NonZeroNumber<N>,
    NegativeNumber<N>
