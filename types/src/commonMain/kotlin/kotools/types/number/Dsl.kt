package kotools.types.number

import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotools.types.tryOrNull

/** Context responsible for building an [IntHolder]. */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
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

/**
 * Context responsible for building a [NonZeroInt].
 */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
public val nonZero: IntHolderDsl<NonZeroInt> = NonZeroHolderDsl

private object NonZeroHolderDsl : IntHolderDsl<NonZeroInt> {
    override fun int(value: Int): NonZeroInt = value.toNonZeroIntOrThrow()
}

/** Context responsible for building a [PositiveInt]. */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
public val positive: IntHolderDsl<PositiveInt> = PositiveHolderDsl

private object PositiveHolderDsl : IntHolderDsl<PositiveInt> {
    override fun int(value: Int): PositiveInt = value.toPositiveInt()
}

/** Context responsible for building a [StrictlyPositiveInt]. */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
public val strictlyPositive: IntHolderDsl<StrictlyPositiveInt> =
    StrictlyPositiveHolderDsl

private object StrictlyPositiveHolderDsl : IntHolderDsl<StrictlyPositiveInt> {
    override fun int(value: Int): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()
}

/** Context responsible for building a [NegativeInt]. */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
public val negative: IntHolderDsl<NegativeInt> = NegativeHolderDsl

private object NegativeHolderDsl : IntHolderDsl<NegativeInt> {
    override fun int(value: Int): NegativeInt = value.toNegativeInt()
}


/** Context responsible for building a [StrictlyNegativeInt]. */
@SinceKotoolsTypes("3.1", StabilityLevel.Alpha)
public val strictlyNegative: IntHolderDsl<StrictlyNegativeInt> =
    StrictlyNegativeHolderDsl

private object StrictlyNegativeHolderDsl : IntHolderDsl<StrictlyNegativeInt> {
    override fun int(value: Int): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()
}
