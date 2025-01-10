package kotools.types.experimental

import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeInt
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
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [kotools.types.experimental.PositiveIntCommonSample.unaryMinusOperator]
 * </details>
 * <br>
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
public operator fun PositiveInt.unaryMinus(): NegativeInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toNegativeInt()
        .getOrNull()
        ?: unexpectedCreationError<NegativeInt>(value)
}
