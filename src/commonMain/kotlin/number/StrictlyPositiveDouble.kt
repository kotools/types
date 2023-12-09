/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.hashCodeOf

/**
 * Represents a floating-point number of type [Double] that is greater than
 * zero.
 *
 * You can use the [StrictlyPositiveDouble.Companion.orNull] function for
 * creating an instance of this type.
 */
@ExperimentalNumberApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
public class StrictlyPositiveDouble private constructor(
    private val value: Double
) : Comparable<StrictlyPositiveDouble> {
    init {
        require(value > 0) {
            "Number should be greater than zero (tried with $value)"
        }
    }

    /**
     * Returns `true` if the [other] object points to the same reference as this
     * floating-point number, or if the [other] object is an instance of
     * [StrictlyPositiveDouble] having the same value as this floating-point
     * number.
     * Returns `false` otherwise.
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
    override fun equals(other: Any?): Boolean =
        if (this === other) true
        else other is StrictlyPositiveDouble && this.value == other.value

    /**
     * Compares this floating-point number with the [other] one for order.
     * Returns zero if this floating-point number equals the [other] one, a
     * strictly negative number if it's less than the [other] one, or a strictly
     * positive number if it's greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int =
        value.compareTo(other.value)

    /** Returns a hash code for this floating-point number. */
    @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
    override fun hashCode(): Int = hashCodeOf(value)

    /** Returns this floating-point number as [Double]. */
    public fun toDouble(): Double = value

    /** Returns this floating-point number as [String]. */
    override fun toString(): String = "$value"

    /** Contains static declarations for the [StrictlyPositiveDouble] type. */
    public companion object {
        /**
         * Creates an instance of [StrictlyPositiveDouble] with the specified
         * [value], or returns `null` if the [value] is less than or equals
         * zero.
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_3_2)
        public fun orNull(value: Double): StrictlyPositiveDouble? =
            if (value <= 0) null
            else StrictlyPositiveDouble(value)
    }
}
