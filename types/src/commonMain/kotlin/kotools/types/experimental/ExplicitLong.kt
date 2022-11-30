package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/** Representation of explicit [Long] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitLong : ExplicitNumber<Long>

/** Representation of [Long] numbers other than zero. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroLong : ExplicitLong,
    NonZeroNumber<Long>

/** Representation of positive [Long] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveLong : ExplicitLong,
    PositiveNumber<Long>

/** Representation of negative [Long] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeLong : ExplicitLong,
    NegativeNumber<Long>

/** Representation of strictly positive [Long] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveLong : NonZeroLong,
    PositiveLong,
    StrictlyPositiveNumber<Long>

/** Representation of strictly negative [Long] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeLong : NonZeroLong,
    NegativeLong,
    StrictlyNegativeNumber<Long>
