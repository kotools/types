@file:Suppress("DEPRECATION")

package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.tryOrNull

/** Context responsible for building an [IntHolder]. */
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
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
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
public val nonZero: IntHolderDsl<NonZeroInt> = NonZeroHolderDsl

private object NonZeroHolderDsl : IntHolderDsl<NonZeroInt> {
    override fun int(value: Int): NonZeroInt = value.nonZero.getOrThrow()
}

/** Context responsible for building a [PositiveInt]. */
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
public val positive: IntHolderDsl<PositiveInt> = PositiveHolderDsl

private object PositiveHolderDsl : IntHolderDsl<PositiveInt> {
    override fun int(value: Int): PositiveInt = value.positive.getOrThrow()
}

/** Context responsible for building a [StrictlyPositiveInt]. */
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
public val strictlyPositive: IntHolderDsl<StrictlyPositiveInt> =
    StrictlyPositiveHolderDsl

private object StrictlyPositiveHolderDsl : IntHolderDsl<StrictlyPositiveInt> {
    override fun int(value: Int): StrictlyPositiveInt =
        value.strictlyPositive.getOrThrow()
}

/** Context responsible for building a [NegativeInt]. */
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
public val negative: IntHolderDsl<NegativeInt> = NegativeHolderDsl

private object NegativeHolderDsl : IntHolderDsl<NegativeInt> {
    override fun int(value: Int): NegativeInt = value.negative.getOrThrow()
}


/** Context responsible for building a [StrictlyNegativeInt]. */
@Deprecated("Use the regular builders instead.")
@SinceKotools(Types, "3.1", StabilityLevel.Alpha)
public val strictlyNegative: IntHolderDsl<StrictlyNegativeInt> =
    StrictlyNegativeHolderDsl

private object StrictlyNegativeHolderDsl : IntHolderDsl<StrictlyNegativeInt> {
    override fun int(value: Int): StrictlyNegativeInt =
        value.strictlyNegative.getOrThrow()
}
