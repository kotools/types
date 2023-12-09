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
 * @constructor Creates an instance of [StrictlyPositiveDouble] with the
 * specified [value], or throws an [IllegalArgumentException] if the [value] is
 * less than or equals zero.
 */
@ExperimentalNumberApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
public class StrictlyPositiveDouble(private val value: Double) {
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
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val value = 1.0
     * val first = StrictlyPositiveDouble(value)
     * val second = StrictlyPositiveDouble(value)
     * val equals: Boolean = first == second // or first.equals(second)
     * println(equals) // true
     * ```
     *
     * It is recommended to use the overloaded operator `==` instead of calling
     * directly this function for better type-safety.
     *
     * ```kotlin
     * val first = StrictlyPositiveDouble(value)
     * val second = "hello world"
     * first == second // produces a compilation error
     * first.equals(second) // compiles and returns `false`
     * ```
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final double value = 1.0;
     * final StrictlyPositiveDouble first = new StrictlyPositiveDouble(value),
     *         second = new StrictlyPositiveDouble(value);
     * final boolean equals = first.equals(second);
     * System.out.println(equals); // true
     * ```
     */
    override fun equals(other: Any?): Boolean = if (this === other) true
    else other is StrictlyPositiveDouble && this.value == other.value

    /** Returns a hash code for this floating-point number. */
    override fun hashCode(): Int = hashCodeOf(value)

    /** Returns this floating-point number as [Double]. */
    public fun toDouble(): Double = value

    /**
     * Returns this floating-point number as [String].
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val number = StrictlyPositiveDouble(2.0)
     * val message = "$number" // or number.toString()
     * println(message) // 2.0
     * ```
     *
     * Here's an example of calling this function from Java code:
     *
     * ```kotlin
     * final StrictlyPositiveDouble number = new StrictlyPositiveDouble(2.0);
     * final String message = number.toString();
     * System.out.println(message); // 2.0
     * ```
     */
    override fun toString(): String = "$value"
}
