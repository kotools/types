package kotools.types.experimental

import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotools.types.number.*
import kotools.types.tryOrNull

/** Context responsible for building an [IntHolder]. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.1", StabilityLevel.Experimental)
public sealed interface IntHolderDsl<out T : IntHolder> {
    /**
     * Returns the [value] as a type [T], or throws an
     * [IllegalArgumentException] if it fails.
     */
    @Throws(IllegalArgumentException::class)
    public infix fun int(value: Int): T

    /** Returns the [value] as a type [T], or returns `null` if it fails. */
    public infix fun intOrNull(value: Int): T? = tryOrNull { int(value) }
}

/** Context responsible for building a [StrictlyPositiveInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val strictlyPositive: IntHolderDsl<StrictlyPositiveInt> =
    StrictlyPositiveHolderDsl

@ExperimentalKotoolsTypesApi
private object StrictlyPositiveHolderDsl : IntHolderDsl<StrictlyPositiveInt> {
    override fun int(value: Int): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()
}

/** Context responsible for building a [NegativeInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val negative: IntHolderDsl<NegativeInt> = NegativeHolderDsl

@ExperimentalKotoolsTypesApi
private object NegativeHolderDsl : IntHolderDsl<NegativeInt> {
    override fun int(value: Int): NegativeInt = value.toNegativeInt()
}

/** Context responsible for building a [StrictlyNegativeInt]. */
@ExperimentalKotoolsTypesApi
@SinceKotoolsTypes("3.0", StabilityLevel.Experimental)
public val strictlyNegative: IntHolderDsl<StrictlyNegativeInt> =
    StrictlyNegativeHolderDsl

@ExperimentalKotoolsTypesApi
private object StrictlyNegativeHolderDsl : IntHolderDsl<StrictlyNegativeInt> {
    override fun int(value: Int): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()
}
