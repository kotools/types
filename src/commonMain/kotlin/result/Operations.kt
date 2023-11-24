/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.result

import kotools.types.experimental.ExperimentalResultApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion

/**
 * Transforms the encapsulated value from [T] to [R] if this result is a
 * [success][Result.isSuccess], or returns the original exception if this result
 * or the one returned by the [transform] function is a
 * [failure][Result.isFailure].
 */
@ExperimentalResultApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
public inline fun <T, R> Result<T>.flatMap(
    transform: (T) -> Result<R>
): Result<R> = exceptionOrNull()
    ?.let(Result.Companion::failure)
    ?: transform(getOrThrow())
