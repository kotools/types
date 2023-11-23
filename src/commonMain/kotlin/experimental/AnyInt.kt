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
import kotlin.jvm.JvmSynthetic

/**
 * Creates an instance of [AnyInt] with the specified [value].
 *
 * ```kotlin
 * val number = AnyInt(1)
 * println(number) // 1
 * ```
 *
 * Please note that this function is not available for Java users because it is
 * inconvenient to use it with this programming language.
 *
 * ```java
 * AnyInt number = AnyIntKt.AnyInt(1); // AnyInt.of(1) would be better
 * System.out.println(number); // 1
 * ```
 *
 * We will provide an equivalent function optimized for Java users soon.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSinceKotoolsTypes("4.3.2")
@JvmSynthetic
public fun AnyInt(value: Int): AnyInt = when {
    value > 0 -> value.toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyPositiveInt>(value)
    value < 0 -> value.toStrictlyNegativeInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyNegativeInt>(value)
    else -> ZeroInt
}

/** Returns the negative of this integer number. */
@ExperimentalKotoolsTypesApi
@ExperimentalSinceKotoolsTypes("4.3.2")
public operator fun AnyInt.unaryMinus(): AnyInt {
    val value: Int = -toInt()
    return AnyInt(value)
}
