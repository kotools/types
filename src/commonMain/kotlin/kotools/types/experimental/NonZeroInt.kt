/*
 * Copyright 2023-2024 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.NonZeroInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toNonZeroInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number: NonZeroInt = 1.toNonZeroInt()
 *     .getOrThrow()
 * val result: NonZeroInt = -number // or number.unaryMinus()
 * println(result) // -1
 * ```
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
public operator fun NonZeroInt.unaryMinus(): NonZeroInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toNonZeroInt()
        .getOrNull()
        ?: unexpectedCreationError<NonZeroInt>(value)
}

/**
 * The negative range of values a [NonZeroInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(NonZeroInt.negativeRange) // [-2147483648;-1]
 * ```
 *
 * Please note that this property is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@get:JvmSynthetic
public val NonZeroInt.Companion.negativeRange:
        NotEmptyRange<StrictlyNegativeInt>
    get() = StrictlyNegativeInt.range

/**
 * The range of positive values a [NonZeroInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(NonZeroInt.positiveRange) // [1;2147483647]
 * ```
 *
 * Please note that this property is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@get:JvmSynthetic
public val NonZeroInt.Companion.positiveRange:
        NotEmptyRange<StrictlyPositiveInt>
    get() = StrictlyPositiveInt.range
