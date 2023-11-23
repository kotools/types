/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.AnyInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt

/** Returns the negative of this integer number. */
@ExperimentalKotoolsTypesApi
@ExperimentalSinceKotoolsTypes("4.3.2")
public operator fun AnyInt.unaryMinus(): AnyInt {
    val value: Int = -toInt()
    return when {
        value > 0 -> value.toStrictlyPositiveInt()
            .getOrNull()
            ?: unexpectedCreationError<StrictlyPositiveInt>(value)
        value < 0 -> value.toStrictlyNegativeInt()
            .getOrNull()
            ?: unexpectedCreationError<StrictlyNegativeInt>(value)
        else -> ZeroInt
    }
}
