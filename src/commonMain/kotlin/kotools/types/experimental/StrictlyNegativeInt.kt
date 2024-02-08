package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
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
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
public operator fun StrictlyNegativeInt.unaryMinus(): StrictlyPositiveInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyPositiveInt>(value)
}

/**
 * The range of values a [StrictlyNegativeInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(StrictlyNegativeInt.range) // [-2147483648;-1]
 * ```
 *
 * Please note that this property is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
@get:JvmSynthetic
public val StrictlyNegativeInt.Companion.range:
        NotEmptyRange<StrictlyNegativeInt>
    get() = rangeValue

@ExperimentalKotoolsTypesApi
private val rangeValue: NotEmptyRange<StrictlyNegativeInt> by lazy {
    val start: StrictlyNegativeInt = Int.MIN_VALUE
        .toStrictlyNegativeInt()
        .getOrThrow()
    val end: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
        .getOrThrow()
    notEmptyRangeOf { start.inclusive to end.inclusive }
}
