/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion

/**
 * Represents a bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_2)
public sealed interface Bound<out T : Comparable<@UnsafeVariance T>> {
    /** The value of this bound. */
    public val value: T

    /** Returns the string representation of this bound's [value]. */
    override fun toString(): String
}

/**
 * Represents an inclusive bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_2)
public class InclusiveBound<out T : Comparable<@UnsafeVariance T>>
internal constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"
}

/**
 * Represents an exclusive bound in a [range][NotEmptyRange].
 *
 * @param T the **covariant** type of this bound's value.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_3_2)
public class ExclusiveBound<out T : Comparable<@UnsafeVariance T>>
internal constructor(override val value: T) : Bound<T> {
    override fun toString(): String = "$value"
}
