/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

@file:JvmName("AnyIntFactory")

package kotools.types.experimental

import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.AnyInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmName

/**
 * Creates an instance of [AnyInt] with the specified [value].
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number = AnyInt(1)
 * println(number) // 1
 * ```
 *
 * The Java function generated from this one is `AnyIntFactory.create`.
 * Here's an example of calling it from Java code:
 *
 * ```java
 * AnyInt number = AnyIntFactory.create(1);
 * System.out.println(number); // 1
 * ```
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSinceKotoolsTypes("4.3.2")
@JvmName("create")
public fun AnyInt(value: Int): AnyInt = when {
    value > 0 -> value.toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyPositiveInt>(value)
    value < 0 -> value.toStrictlyNegativeInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyNegativeInt>(value)
    else -> ZeroInt
}
