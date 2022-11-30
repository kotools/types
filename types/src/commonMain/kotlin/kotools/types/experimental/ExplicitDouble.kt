package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/** Representation of explicit [Double] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitDouble : ExplicitNumber<Double>

/** Representation of [Double] numbers other than zero. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroDouble : ExplicitDouble,
    NonZeroNumber<Double>

/** Representation of positive [Double] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveDouble : ExplicitDouble,
    PositiveNumber<Double>

/** Representation of negative [Double] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeDouble : ExplicitDouble,
    NegativeNumber<Double>

/** Representation of strictly positive [Double] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveDouble : NonZeroDouble,
    PositiveDouble,
    StrictlyPositiveNumber<Double>

/** Representation of strictly negative [Double] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeDouble : NonZeroDouble,
    NegativeDouble,
    StrictlyNegativeNumber<Double>
