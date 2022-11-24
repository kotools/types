package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/** Representation of explicit [Float] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitFloat : ExplicitNumber<Float>

/** Representation of [Float] numbers other than zero. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroFloat : ExplicitFloat,
    NonZeroNumber<Float>

/** Representation of positive [Float] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveFloat : ExplicitFloat,
    PositiveNumber<Float>

/** Representation of negative [Float] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeFloat : ExplicitFloat,
    NegativeNumber<Float>

/** Representation of strictly positive [Float] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveFloat : NonZeroFloat,
    PositiveFloat,
    StrictlyPositiveNumber<Float>

/** Representation of strictly negative [Float] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeFloat : NonZeroFloat,
    NegativeFloat,
    StrictlyNegativeNumber<Float>
