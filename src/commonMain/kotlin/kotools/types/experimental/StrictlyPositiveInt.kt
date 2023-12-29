/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number: StrictlyPositiveInt = 1.toStrictlyPositiveInt()
 *     .getOrThrow()
 * val result: StrictlyNegativeInt = -number // or number.unaryMinus()
 * println(result) // -1
 * ```
 *
 * Please note that this function is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmSynthetic
public operator fun StrictlyPositiveInt.unaryMinus(): StrictlyNegativeInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toStrictlyNegativeInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyNegativeInt>(value)
}

/**
 * The range of values a [StrictlyPositiveInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(StrictlyPositiveInt.range) // [1;2147483647]
 * ```
 *
 * Please note that this property is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@get:JvmSynthetic
public val StrictlyPositiveInt.Companion.range:
        NotEmptyRange<StrictlyPositiveInt>
    get() = rangeValue

@ExperimentalKotoolsTypesApi
private val rangeValue: NotEmptyRange<StrictlyPositiveInt> by lazy {
    val start = StrictlyPositiveInt(1)
    val end = StrictlyPositiveInt(Int.MAX_VALUE)
    notEmptyRangeOf { start.inclusive to end.inclusive }
}
