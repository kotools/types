package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Returns a not empty range with the given pair of [bounds].
 * The resulting range will [start][NotEmptyRange.start] with the lowest value
 * between the given [bounds].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
public fun <T : Comparable<T>> notEmptyRangeOf(
    bounds: NotEmptyRange.BuilderScope<T>.() -> Pair<Bound<T>, Bound<T>>
): NotEmptyRange<T> = NotEmptyRange.BuilderScope.create<T>()
    .bounds()
    .run {
        if (first.value < second.value) NotEmptyRange.of(
            start = first,
            end = second
        )
        else NotEmptyRange.of(start = second, end = first)
    }

/**
 * Represents a range of comparable values that contain at least one value.
 *
 * You can use the [notEmptyRangeOf] function for creating an instance of this
 * type.
 *
 * @param T the **covariant** type of values in this range.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
public class NotEmptyRange<out T : Comparable<@UnsafeVariance T>>
private constructor(
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

    /** Contains static declarations for the [NotEmptyRange] type. */
    public companion object {
        @JvmSynthetic
        internal fun <T : Comparable<T>> of(
            start: Bound<T>,
            end: Bound<T>
        ): NotEmptyRange<T> = NotEmptyRange(start, end)
    }

    /** Class responsible for configuring an instance of [NotEmptyRange]. */
    public class BuilderScope<T : Comparable<T>> private constructor() {
        /** Returns this value as an inclusive bound. */
        public val T.inclusive: InclusiveBound<T> get() = InclusiveBound of this

        /** Returns this value as an exclusive bound. */
        public val T.exclusive: ExclusiveBound<T> get() = ExclusiveBound of this

        /** Contains static declarations for the [BuilderScope] type. */
        public companion object {
            @JvmSynthetic
            internal fun <T : Comparable<T>> create(): BuilderScope<T> =
                BuilderScope()
        }
    }
}

/**
 * Returns `true` if this range contains the given [value], or returns `false`
 * otherwise.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
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
