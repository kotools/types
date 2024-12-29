package kotools.types.experimental

import kotools.types.number.AnyInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this method from Kotlin code:
 *
 * SAMPLE: [kotools.types.experimental.AnyIntCommonSample.unaryMinusOperator]
 * </details>
 * <br>
 *
 * Please note that this method is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
public operator fun AnyInt.unaryMinus(): AnyInt {
    val value: Int = toInt()
        .unaryMinus()
    return AnyInt(value)
}
