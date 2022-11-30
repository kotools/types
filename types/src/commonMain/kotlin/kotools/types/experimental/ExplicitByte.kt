package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental

/** Representation of explicit [Byte] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface ExplicitByte : ExplicitNumber<Byte>

/** Representation of [Byte] numbers other than zero. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroByte : ExplicitByte,
    NonZeroNumber<Byte>

/** Representation of positive [Byte] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface PositiveByte : ExplicitByte,
    PositiveNumber<Byte>

/** Representation of negative [Byte] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NegativeByte : ExplicitByte,
    NegativeNumber<Byte>

/** Representation of strictly positive [Byte] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyPositiveByte : NonZeroByte,
    PositiveByte,
    StrictlyPositiveNumber<Byte>

/** Representation of strictly negative [Byte] numbers. */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface StrictlyNegativeByte : NonZeroByte,
    NegativeByte,
    StrictlyNegativeNumber<Byte>
