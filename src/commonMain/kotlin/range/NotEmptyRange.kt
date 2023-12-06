/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.range

import kotools.types.experimental.Bound
import kotools.types.experimental.ExclusiveBound
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.experimental.InclusiveBound
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion

/**
 * Returns a not empty range with the given pair of [bounds].
 * The resulting range will [start][NotEmptyRange.start] with the lowest value
 * between the given [bounds].
 */
@ExperimentalRangeApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@OptIn(ExperimentalKotoolsTypesApi::class)
public fun <T : Comparable<T>> notEmptyRangeOf(
    bounds: NotEmptyRange.BuilderScope<T>.() -> Pair<Bound<T>, Bound<T>>
): NotEmptyRange<T> = NotEmptyRange.BuilderScope<T>()
    .bounds()
    .run {
        if (first.value < second.value) NotEmptyRange(
            start = first,
            end = second
        )
        else NotEmptyRange(start = second, end = first)
    }

/**
 * Represents a range of comparable values that contain at least one value.
 * @param T the **covariant** type of values in this range.
 */
@ExperimentalRangeApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@OptIn(ExperimentalKotoolsTypesApi::class)
public class NotEmptyRange<out T : Comparable<@UnsafeVariance T>>
internal constructor(
    /** The start of this range. */
    public val start: Bound<T>,
    /** The end of this range. */
    public val end: Bound<T>
) {
    /** Returns the string representation of this range. */
    override fun toString(): String {
        val prefix: Char = when (start) {
            is InclusiveBound -> '['
            is ExclusiveBound -> ']'
        }
        val suffix: Char = when (end) {
            is InclusiveBound -> ']'
            is ExclusiveBound -> '['
        }
        return "$prefix$start;$end$suffix"
    }

    /** Class responsible for configuring an instance of [NotEmptyRange]. */
    public class BuilderScope<T : Comparable<T>> internal constructor() {
        /** Returns this value as an inclusive bound. */
        public val T.inclusive: InclusiveBound<T> get() = InclusiveBound(this)

        /** Returns this value as an exclusive bound. */
        public val T.exclusive: ExclusiveBound<T> get() = ExclusiveBound(this)
    }
}

/**
 * Returns `true` if this range contains the given [value], or returns `false`
 * otherwise.
 */
@ExperimentalRangeApi
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@OptIn(ExperimentalKotoolsTypesApi::class)
public operator fun <T : Comparable<T>> NotEmptyRange<T>.contains(
    value: T
): Boolean {
    val valueIsGreaterThanStart: Boolean = when (start) {
        is InclusiveBound -> value >= start.value
        is ExclusiveBound -> value > start.value
    }
    val valueIsLowerThanEnd: Boolean = when (end) {
        is InclusiveBound -> value <= end.value
        is ExclusiveBound -> value < end.value
    }
    return valueIsGreaterThanStart && valueIsLowerThanEnd
}
