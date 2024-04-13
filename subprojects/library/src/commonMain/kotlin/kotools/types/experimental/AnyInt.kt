package kotools.types.experimental

import kotools.types.number.AnyInt
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
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
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
public operator fun AnyInt.unaryMinus(): AnyInt {
    val value: Int = toInt()
        .unaryMinus()
    return AnyInt(value)
}
