package kotools.types.number

import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/** Represents strictly positive floating-point numbers represented by the [Double] type. */
@SinceKotoolsTypes("4.2")
public sealed interface StrictlyPositiveDouble :
    Comparable<StrictlyPositiveDouble> {
    /**
     * Compares this floating-point number with the other one for order.
     * Returns zero if this floating-point number equals the other one,
     * a negative number if it's less than the other one,
     * or a positive number if it's greater than the other one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int {
        val x: Double = toDouble()
        val y: Double = other.toDouble()
        return x.compareTo(y)
    }

    /** Returns this floating-point number as a [Double]. */
    public fun toDouble(): Double

    /** Returns the string representation of this floating-point number. */
    override fun toString(): String
}

@JvmInline
private value class StrictlyPositiveDoubleImplementation(
    private val value: Double
) : StrictlyPositiveDouble {
    override fun toDouble(): Double = value
    override fun toString(): String = "$value"
}

/**
 * Returns this number as an encapsulated [StrictlyPositiveDouble], which may involve rounding or truncation,
 * or returns an encapsulated [IllegalArgumentException] if this number is negative.
 */
@SinceKotoolsTypes("4.2")
public fun Number.toStrictlyPositiveDouble(): Result<StrictlyPositiveDouble> =
    runCatching {
        val value: Double = toDouble()
        require(value > 0.0) {
            "Number should be strictly positive (tried with $value)."
        }
        StrictlyPositiveDoubleImplementation(value)
    }
