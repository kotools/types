package kotools.types.experimental

import kotools.types.number.AnyInt
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number = AnyInt(1)
 * val result: AnyInt = -number // or number.unaryMinus()
 * println(result) // -1
 * ```
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@JvmSynthetic
public operator fun AnyInt.unaryMinus(): AnyInt {
    val value: Int = toInt()
        .unaryMinus()
    return AnyInt(value)
}
