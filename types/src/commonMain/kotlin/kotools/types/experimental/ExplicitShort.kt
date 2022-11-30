package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/** Representation of explicit [Short] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitShort : ExplicitNumber<Short>

/** Representation of [Short] numbers other than zero. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroShort : ExplicitShort,
    NonZeroNumber<Short>

/** Representation of positive [Short] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveShort : ExplicitShort,
    PositiveNumber<Short>

/** Representation of negative [Short] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeShort : ExplicitShort,
    NegativeNumber<Short>

/** Representation of strictly positive [Short] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveShort : NonZeroShort,
    PositiveShort,
    StrictlyPositiveNumber<Short>

/** Representation of strictly negative [Short] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeShort : NonZeroShort,
    NegativeShort,
    StrictlyNegativeNumber<Short>
