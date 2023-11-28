/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
 *     .getOrThrow()
 * val result: StrictlyPositiveInt = -number // or number.unaryMinus()
 * println(result) // 1
 * ```
 *
 * Please note that this function is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_3)
@JvmSynthetic
public operator fun StrictlyNegativeInt.unaryMinus(): StrictlyPositiveInt {
    val value: Int = -toInt()
    return value.toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyPositiveInt>(value)
}
