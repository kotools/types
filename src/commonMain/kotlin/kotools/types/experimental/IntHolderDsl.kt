package kotools.types.experimental

import kotools.types.ExperimentalKotoolsTypesApi
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotools.types.tryOrNull
import kotools.types.number.IntHolder
import kotools.types.number.NegativeInt
import kotools.types.number.NonZeroInt
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toNegativeInt
import kotools.types.number.toNonZeroInt
import kotools.types.number.toPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt

@ExperimentalKotoolsTypesApi
internal sealed interface IntHolderDsl<out T : IntHolder> {
    /**
     * Returns the [value] as a type [T], or throws an
     * [IllegalArgumentException] if it fails.
     */
    @Throws(IllegalArgumentException::class)
    infix fun int(value: Int): T

    /** Returns the [value] as a type [T], or returns `null` if it fails. */
    infix fun intOrNull(value: Int): T? = tryOrNull { int(value) }
}

/** Holds the context responsible for building a non-zero number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val nonZero: NonZeroHolderDsl = NonZeroHolderDsl

/** Context responsible for building a non-zero number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public object NonZeroHolderDsl : IntHolderDsl<NonZeroInt> {
    override fun int(value: Int): NonZeroInt = value.toNonZeroInt()
}

/** Context responsible for building a positive number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val positive: PositiveHolderDsl = PositiveHolderDsl

/** Context responsible for building a positive number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public object PositiveHolderDsl : IntHolderDsl<PositiveInt> {
    override fun int(value: Int): PositiveInt = value.toPositiveInt()
}

/** Context responsible for building a strictly positive number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val strictlyPositive: StrictlyPositiveHolderDsl =
    StrictlyPositiveHolderDsl

/** Context responsible for building a strictly positive number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public object StrictlyPositiveHolderDsl : IntHolderDsl<StrictlyPositiveInt> {
    override fun int(value: Int): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()
}

/** Context responsible for building a negative number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val negative: NegativeHolderDsl = NegativeHolderDsl

/** Context responsible for building a negative number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public object NegativeHolderDsl : IntHolderDsl<NegativeInt> {
    override fun int(value: Int): NegativeInt = value.toNegativeInt()
}

/** Context responsible for building a strictly negative number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val strictlyNegative: StrictlyNegativeHolderDsl =
    StrictlyNegativeHolderDsl

/** Context responsible for building a strictly negative number. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public object StrictlyNegativeHolderDsl : IntHolderDsl<StrictlyNegativeInt> {
    override fun int(value: Int): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()
}
