/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion

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
