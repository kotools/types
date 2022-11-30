package kotools.types.experimental.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.experimental.ExperimentalKotoolsTypesApi

/**
 * Representation of explicit numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", StabilityLevel.Experimental)
public sealed interface ExplicitNumber<out N : Number> {
    /** The value to hold. */
    public val value: N
}

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", StabilityLevel.Experimental)
public operator fun Int.compareTo(other: ExplicitNumber<Int>): Int =
    compareTo(other.value)

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", StabilityLevel.Experimental)
public operator fun ExplicitNumber<Int>.compareTo(other: Int): Int =
    value.compareTo(other)
