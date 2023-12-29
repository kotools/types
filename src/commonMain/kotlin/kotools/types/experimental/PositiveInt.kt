/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.number.toNegativeInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number: PositiveInt = 1.toPositiveInt()
 *     .getOrThrow()
 * val result: NegativeInt = -number // or number.unaryMinus()
 * println(result) // -1
 * ```
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmSynthetic
public operator fun PositiveInt.unaryMinus(): NegativeInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toNegativeInt()
        .getOrNull()
        ?: unexpectedCreationError<NegativeInt>(value)
}

/**
 * The range of values a [PositiveInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(PositiveInt.range) // [0;2147483647]
 * ```
 *
 * Please note that this property is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@get:JvmSynthetic
public val PositiveInt.Companion.range: NotEmptyRange<PositiveInt>
    get() = rangeValue

@ExperimentalKotoolsTypesApi
private val rangeValue: NotEmptyRange<PositiveInt> by lazy {
    val end: StrictlyPositiveInt = StrictlyPositiveInt.range.end.value
    notEmptyRangeOf { ZeroInt.inclusive to end.inclusive }
}
