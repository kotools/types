package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.ZeroInt
import kotools.types.number.toPositiveInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number: NegativeInt = (-1).toNegativeInt()
 *     .getOrThrow()
 * val result: PositiveInt = -number // or number.unaryMinus()
 * println(result) // 1
 * ```
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
public operator fun NegativeInt.unaryMinus(): PositiveInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<PositiveInt>(value)
}

/**
 * The range of values a [NegativeInt] can have.
 *
 * Here's an example of calling this property from Kotlin code:
 *
 * ```kotlin
 * println(NegativeInt.range) // [-2147483648;0]
 * ```
 *
 * Please note that this property is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
@get:JvmSynthetic
public val NegativeInt.Companion.range: NotEmptyRange<NegativeInt>
    get() = rangeValue

@ExperimentalKotoolsTypesApi
private val rangeValue: NotEmptyRange<NegativeInt> by lazy {
    val start: StrictlyNegativeInt =
        StrictlyNegativeInt.range.start.value
    notEmptyRangeOf { start.inclusive to ZeroInt.inclusive }
}
