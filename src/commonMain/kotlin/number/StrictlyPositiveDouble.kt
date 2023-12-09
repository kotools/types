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
}
